package com.rsvti.backup;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.FileContent;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import com.rsvti.model.database.DBServices;

public class GoogleDriveBackup {
    /** Application name. */
    private static final String APPLICATION_NAME = "RSVTI App";

    /** Directory to store user credentials for this application. */
    private static final java.io.File DATA_STORE_DIR = new java.io.File(System.getProperty("user.home"), ".credentials/drive-java-quickstart");

    /** Global instance of the {@link FileDataStoreFactory}. */
    private static FileDataStoreFactory DATA_STORE_FACTORY;

    /** Global instance of the JSON factory. */
    private static final JsonFactory JSON_FACTORY =
        JacksonFactory.getDefaultInstance();

    /** Global instance of the HTTP transport. */
    private static HttpTransport HTTP_TRANSPORT;

    /** Global instance of the scopes required by this quickstart.
     *
     * If modifying these scopes, delete your previously saved credentials
     * at ~/.credentials/drive-java-quickstart
     */
    private static final List<String> SCOPES =
        Arrays.asList(DriveScopes.DRIVE);

    static {
        try {
            HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
            DATA_STORE_FACTORY = new FileDataStoreFactory(DATA_STORE_DIR);
        } catch (Throwable t) {
            t.printStackTrace();
            System.exit(1);
        }
    }
    
    private final static String FILE_UPLOAD_FIELDS = "id";
    private final static String FILE_SEARCH_FIELDS = "id, name, createdTime";
    private static Drive service;
    private static final int numberOfOnlineCopies = 5;
    private static int delay = 500;

    /**
     * Creates an authorized Credential object.
     * @return an authorized Credential object.
     * @throws IOException
     */
    private static Credential authorize() throws IOException {
        // Load client secrets.
        InputStream in = GoogleDriveBackup.class.getResourceAsStream("/client_secret.json");
        GoogleClientSecrets clientSecrets =
            GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow =
                new GoogleAuthorizationCodeFlow.Builder(
                        HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(DATA_STORE_FACTORY)
                .setAccessType("offline")
                .build();
        Credential credential = new AuthorizationCodeInstalledApp(
            flow, new LocalServerReceiver()).authorize("user");
        System.out.println("Credentials saved to " + DATA_STORE_DIR.getAbsolutePath());
        return credential;
    }

    /**
     * Build and return an authorized Drive client service.
     * @return an authorized Drive client service
     * @throws IOException
     */
    private static Drive getDriveService() throws IOException {
        Credential credential = authorize();
        return new Drive.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, credential)
                .setApplicationName(APPLICATION_NAME)
                .build();
    }
    
    public static void initialize() {
    	try {
    		service = getDriveService();
    	} catch(Exception e) {
    		DBServices.saveErrorLogEntry(e);
    	}
    }
    
	private static void downloadFile(Drive service, File file) {
		try {
			while(!connected());
			if(!file.getName().contains("ErrorLog")) {
				java.io.File localFile = new java.io.File("database/" + file.getName() + ".xml ");
				if(localFile.exists()) {
					localFile.delete();
				}
				OutputStream outputStream = new FileOutputStream("database/" + file.getName() + ".xml ");
				service.files().get(file.getId()).executeMediaAndDownloadTo(outputStream);
				outputStream.close();
			}
		} catch(Exception e) {
			DBServices.saveErrorLogEntry(e);
		}
	}
	
	public static void updateLocalDBFiles() {
		try {
			while(!connected());
	        FileList result = service.files().list()
	             .setPageSize(100)
	             .setFields("nextPageToken, files(" + FILE_SEARCH_FIELDS + ")")
	             .execute();
	        List<File> files = result.getFiles();
			for(File index : getLatestVersions(files)) {
				downloadFile(service, index);
			}
		} catch(Exception e) {
			DBServices.saveErrorLogEntry(e);
		}
	}
	
	public static void uploadFile(String fileName) {
		try {
			while(!connected());
			String tmp = "";
			for(int i = 3; i < Thread.currentThread().getStackTrace().length; i++) {
				tmp += " -> " + Thread.currentThread().getStackTrace()[i].getMethodName();
			}
			System.out.println("Uploading " + fileName + " From " + tmp);
			java.io.File localFile = new java.io.File(com.rsvti.common.Utils.getJarFilePath() + "database\\" + fileName);
			File fileMetadata = new File();
			fileMetadata.setName(localFile.getName().substring(0, localFile.getName().lastIndexOf(".")));
			FileContent mediaContent = new FileContent("/database", localFile);
			service.files().create(fileMetadata, mediaContent)
			    .setFields(FILE_UPLOAD_FIELDS)
			    .execute();
		} catch (GoogleJsonResponseException g) {
			if(g.getContent().contains("User Rate Limit Exceeded")) {
				try {
					//only when uploading all at once with Data.populate
					Thread.sleep(delay);
					delay+=500;
					uploadFile(fileName);
				} catch(Exception e) {}
			}
		} catch(Exception e) {
			DBServices.saveErrorLogEntry(e);
		} finally {
			deleteExtraCopies();
		}
	}
	
	/**
	 * For programming purposes only.
	 */
	private static void deleteAllFiles(Drive driveService, List<File> files) {
		try {
			while(!connected());
			System.out.println("Deleting files...");
			int i = 1;
			double size = files.size();
			final DecimalFormat df = new DecimalFormat("###.##");
	    	for (File file : files) {
	    		driveService.files().delete(file.getId()).execute();
	    		System.out.println("Progress: " + df.format((i*100)/size) + "%");
	    		i++;
	        }
	    	System.out.println("DONE!");
		} catch(Exception e) {
			DBServices.saveErrorLogEntry(e);
		}
	}
	
	private static List<File> getAllFiles() {
		List<File> files = new ArrayList<>();
		try {
			FileList result = service.files().list().setPageSize(100).setFields("nextPageToken, files(" + FILE_SEARCH_FIELDS + ")").execute();
			files = result.getFiles();
		} catch (GoogleJsonResponseException g) {
			if(!g.getContent().contains("User Rate Limit Exceeded")) {
				DBServices.saveErrorLogEntry(g);
			}
		} catch(Exception e) {
			DBServices.saveErrorLogEntry(e);
		}
		return files;
	}
	
	private static void deleteExtraCopies() {
		try {
			while(!connected());
			List<List<File>> lists = getFilesByTypes(getAllFiles());
			for(List<File> index : lists) {
				for(int i = numberOfOnlineCopies; i < index.size(); i++) {
					System.out.println("Deleting extra copies: " + index.get(i).getName());
					service.files().delete(index.get(i).getId()).execute();
				}
			}
		} catch (GoogleJsonResponseException g) {
			if(!g.getContent().contains("User Rate Limit Exceeded") && !g.getContent().contains("File not found")) {
				DBServices.saveErrorLogEntry(g);
			}
		} catch(Exception e) {
			DBServices.saveErrorLogEntry(e);
		}
	}
	
	private static List<List<File>> getFilesByTypes(List<File> files) {
		Collections.sort(files, (e1, e2) -> e1.getName().compareToIgnoreCase(e2.getName()));
		List<List<File>> lists = new ArrayList<>();
		List<File> sameNameFiles = new ArrayList<>();
		for(int i = 1; i < files.size(); i++) {
			if(files.get(i).getName().equals(files.get(i-1).getName())) {
				sameNameFiles.add(files.get(i-1));
			} else {
				sameNameFiles.add(files.get(i-1));
				lists.add(sameNameFiles);
				sameNameFiles = new ArrayList<>();
			}
			if(i == files.size() - 1) {
				if(files.get(i).getName().equals(files.get(i-1).getName())) {
					sameNameFiles.add(files.get(i));
					lists.add(sameNameFiles);
				} else {
					sameNameFiles = new ArrayList<>();
					sameNameFiles.add(files.get(i));
					lists.add(sameNameFiles);
				}
			}
		}
		return lists;
	}
	
	private static List<File> getLatestVersions(List<File> files) {
		deleteExtraCopies();
		List<File> latestVersions = new ArrayList<>();
		List<List<File>> lists = getFilesByTypes(getAllFiles());
		for(List<File> index : lists) {
			Collections.sort(index, (e1, e2) -> new Date(e1.getCreatedTime().getValue()).compareTo(new Date(e2.getCreatedTime().getValue())));
			latestVersions.add(index.get(index.size() - 1));
		}
		return latestVersions;
	}
	
	public static boolean connected() {
		try {
			service.files().list()
            .setPageSize(100)
            .setFields("nextPageToken, files(" + FILE_SEARCH_FIELDS + ")")
            .execute();
		} catch(Exception e) {
			return false;
		}
		return true;
	}
	
    public static void main(String[] args) throws IOException {
//    	try {
//    		initialize();
//    		while(!connected());
//	        FileList result = service.files().list()
//	             .setPageSize(1000)
//	             .setFields("nextPageToken, files(" + FILE_SEARCH_FIELDS + ")")
//	             .execute();
//	        List<File> files = result.getFiles();
//	        System.out.println(files.size());
//	        if (files == null || files.size() == 0) {
//	            System.out.println("No files found.");
//	        } else {
//	            System.out.println("Files:");
//	            for (File file : files) {
//	            	System.out.printf("%s (%s) %s\n", file.getName(), file.getId(),
//	            			new SimpleDateFormat("dd.MM.yyyy hh:mm:ss.SSS").format(
//	            					new Date(file.getCreatedTime() == null ? 0 : file.getCreatedTime().getValue())));
//	            }
//	        }
//	        deleteAllFiles(service, files);
//    	} catch(Exception e) {
//    		e.printStackTrace();
//    	}
    }
}
