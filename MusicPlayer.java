//student: Hossein_Jafari_2986457

//standard javafx libraries
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;

//imports for components in the application
import javafx.scene.control.Label; 
import javafx.scene.control.ListView;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.control.Slider;

//imports for layout
import javafx.scene.layout.GridPane;
import javafx.scene.image.Image;
import javafx.geometry.Insets;

//import File to get music file
import java.io.File;

//import ObservableList and FXCollections to use ListView with getItems() method
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;

//import ChangeListener to detect a selection change in either of ListViews
import javafx.beans.value.ChangeListener;
import javafx.beans.InvalidationListener;
 
//import ObservableValue for event method of selection change in ListView
import javafx.beans.value.ObservableValue;
import javafx.beans.Observable;

//import MediaPlayer to play Mp3 files
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;

//import Duration to get duration status of playing music  
import javafx.util.Duration;


public class MusicPlayer extends Application {
	
	//create labels
	Label lblTracks, lblPlayList, lblVolume, lblStatus; 
	
	//create list views
	ListView <String> lvAvailableTracks, lvSelectedTracks;
	
	//create buttons
	Button btnAdd, btnRemove, btnRemoveAll, btnPlay, btnPause, btnStop;
	
	//create sliders
	Slider slrVolume, slrStatus;
	
	//create media and mediaPlayer
	MediaPlayer mediaPlayer;
	Media media;
	
	public MusicPlayer(){
		
	}//constructor()
	
	public void init () {
		
		//instantiate btnAdd
		btnAdd= new Button("Add >");
		
		//disable button by default 
		btnAdd.setDisable(true);
		
		//handle event when clicking on button 
		btnAdd.setOnAction(ae -> addMusicFile());
	
		//instantiate lblTracks
		lblTracks= new Label ("Available Tracks:"); 
		
		//instantiate lvAvailableTracks
		lvAvailableTracks= new ListView <String>();
		
		//event handler for selecting an item in lvAvailableTracks
		lvAvailableTracks.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
			
			//event handler function for selection change
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				
				//enable btnAdd when an item is selected in lvAvailableTracks
				btnAdd.setDisable(false);
			}
		});
		
		//instantiate btnRemove
		btnRemove = new Button("< Remove");
		
		//disable button by default 
		btnRemove.setDisable(true);	
		
		//handle event when clicking on button 
		btnRemove.setOnAction(ae -> removeItemFromSelectedTracks());
		
		//instantiate btnRemove
		btnRemoveAll = new Button("<< Remove All");
		
		//disable button by default 
		btnRemoveAll.setDisable(true);
		
		//handle event when clicking on button
		btnRemoveAll.setOnAction(ae -> removeAllItemsFromSelectedTracks()); 
		
		//instantiate btnPlay 
		btnPlay = new Button("Play");
		
		//handle event when clicking on button
		btnPlay.setOnAction(ae -> playSelectedTrack());
		
		//disable button by default 
		btnPlay.setDisable(true);
		
		//instantiate btnPause 
		btnPause = new Button("Pause");
		
		//handle event when clicking on button
		btnPause.setOnAction(ae -> pauseSelectedTrack());
		
		//disable button by default 
		btnPause.setDisable(true);
		
		//instantiate btnStop
		btnStop = new Button("Stop");
		
		//handle event when clicking on button
		btnStop.setOnAction(ae -> stopSelectecTrack());
		
		//disable button by default 
		btnStop.setDisable(true);
		
		//instantiate label
		lblPlayList= new Label ("Selected Tracks:");
		
		//instantiate list view
		lvSelectedTracks= new ListView <String>();
		
		//adding event handler for selecting an item in lvAvailableTracks
		lvSelectedTracks.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
			
			//event handler function for selection change in selected tracks list view
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				
				//enable btnRemove, btnRemoveAll and btnPlay when an item is selected in lvSelectedTracks list view
				btnRemove.setDisable(false);
				btnRemoveAll.setDisable(false);
				btnPlay.setDisable(false);
			}
		});		
		
		//manage listViews width and Height.
		lvAvailableTracks.setMinWidth(50);
		lvAvailableTracks.setMinHeight(100);
		lvSelectedTracks.setMinWidth(50);
		lvSelectedTracks.setMinHeight(100);
		
		//instantiate lblVolume and lblStatus
		lblVolume = new Label("Volume:");
		lblStatus = new Label("Status");
		
		//instantiate sliders
		slrVolume = new Slider();
		slrStatus = new Slider();
		
		//manage buttons minimum width
		int btnMinWidth=115;
		btnAdd.setMinWidth(btnMinWidth);
		btnRemove.setMinWidth(btnMinWidth);
		btnRemoveAll.setMinWidth(btnMinWidth); 
		btnPlay.setMinWidth(btnMinWidth);
		btnPause.setMinWidth(btnMinWidth);
		btnStop.setMinWidth(btnMinWidth);
		
		//add .mp3 files into available tracks listView
		lvAvailableTracks.setItems(getFilesInFolder("music"));
		
	}//init()
	
	//method to list all files inside a folder
	private ObservableList<String> getFilesInFolder(String folderName){
		
		//ovservableList for the music files.
		ObservableList <String> musicFiles = FXCollections.observableArrayList();
		
		//string array to store a list of playable files.
		String [] fileList; 
		
		//instantiate File
		File f = new File(folderName);
		
		//Call list() to get a directory listing.
		fileList= f.list();
		
		//add the array of files to the music files observable list.
		musicFiles.addAll(fileList);
	
		//return the observable list. 
		return musicFiles; 
		
	}//getFilesInFolder()
	
	private void addMusicFile(){
		
		//get the item from lvAvailableTracks and add them to lvSelectedTracks
		lvSelectedTracks.getItems().add(lvAvailableTracks.getSelectionModel().getSelectedItem());
		
		//get the index of selected item in lvAvailableTracks list view
		int selectedIdx = lvAvailableTracks.getSelectionModel().getSelectedIndex();
		
		//check if index is not -1 which means an item is actually selected
		if (selectedIdx != -1) {
			
			//remove the selected item using the selection index
			lvAvailableTracks.getItems().remove(selectedIdx);
		}
		
	}//addMusicFile()
	
	private void removeItemFromSelectedTracks(){ 
		
		//get the item from lvSelectedTracks and add them to lvAvailableTracks
		lvAvailableTracks.getItems().add(lvSelectedTracks.getSelectionModel().getSelectedItem());
		
		//get the index of selected item in lvSelectedTracks list view
		int selectedIdx = lvSelectedTracks.getSelectionModel().getSelectedIndex();
		
		//check if index is not -1 which means an item is actually selected
		if (selectedIdx != -1) {
			
			//remove the selected item using the selection index
			lvSelectedTracks.getItems().remove(selectedIdx);
		}
		
		//disable remove, remove all and play buttons if no item is left in selected tracks list view
		if (lvSelectedTracks.getItems().size() == 0){
			btnRemoveAll.setDisable(true);
			btnRemove.setDisable(true);
			btnPlay.setDisable(true);
		}
		
	}//removeAllItemsFromSelectedTracks()
	
	private void removeAllItemsFromSelectedTracks(){
		
		//while there is some items in selected track, remove the first item from selected track
		while(lvSelectedTracks.getItems().size() > 0){
			
			//add the first item to available tracks list view 
			lvAvailableTracks.getItems().add(lvSelectedTracks.getItems().get(0));
			
			//remove the first item from selected tracks list view
			lvSelectedTracks.getItems().remove(0);
		}
		
		//disabling btnRemove, btnRemove and btnPlay all buttons
		btnRemove.setDisable(true);
		btnRemoveAll.setDisable(true);
		btnPlay.setDisable(true);
		
	}//removeAllItemsFromSelectedTracks()
	
	private void playSelectedTrack(){
		
		//get the index of selected item in lvSelectedTracks list view 
		int selectedIdx = lvSelectedTracks.getSelectionModel().getSelectedIndex();
		
		//check if index is not -1 which means an item is actually selected
		if (selectedIdx != -1) {
			
			//use mediaPlayer to play music 
			if (mediaPlayer != null && mediaPlayer.getStatus() == Status.PAUSED){
				mediaPlayer.play();
			}
			else{
				String musicToPlay = lvSelectedTracks.getItems().get(selectedIdx);
         
				//create a Media 
				String path = "./music/" + musicToPlay;
				media = new Media(new File(path).toURI().toString());
				mediaPlayer = new MediaPlayer(media);
				
				mediaPlayer.currentTimeProperty().addListener((InvalidationListener) ov -> {		
				Duration trackDuration = mediaPlayer.getTotalDuration();			
				Duration duration = mediaPlayer.getCurrentTime();
				
				//calculate the track slider position.
				slrStatus.setValue((duration.toSeconds()*100)/trackDuration.toSeconds());
				
				//show the current track position in a label.					
				double minutes = Math.floor(duration.toMinutes());
				double seconds = Math.floor(duration.toSeconds() % 60);
				
				int m = (int) minutes;
				int s = (int) seconds;
				
				//show played time on lblStatus
				lblStatus.setText("Status: Playing " + m + ":" + s);	
				
				});
				
				//set the position of slider according to volume of the player 
				slrVolume.setValue(mediaPlayer.getVolume() * 100);
				
				//add event handler to the event of changing slider 
				slrVolume.valueProperty().addListener(new InvalidationListener(){
					
					@Override 
					//set the volume of player according to value of slider 
					public void invalidated(Observable observable) {
						mediaPlayer.setVolume(slrVolume.getValue() / 100);
					}
				});
				
				//play music
				mediaPlayer.play();
				
			}
			
			//disable btnPlay
			btnPlay.setDisable(true);
			
			//enable btnPause and btnStop
			btnPause.setDisable(false);
			btnStop.setDisable(false);	
		}
		
	}//playSelectedTrack()
	
	private void pauseSelectedTrack(){
		
		//pause playing if it is in play mode
		if (mediaPlayer.getStatus() == Status.PLAYING){
			mediaPlayer.pause();
			
			//enable btnPlay button
			btnPlay.setDisable(false);
			
			//disable btnPause and btnStop
			btnPause.setDisable(true);
			btnStop.setDisable(true);
		}
		
	}//pauseSelectedTrack()
	
	private void stopSelectecTrack(){
		
		//stop playing if it is in play mode
		if (mediaPlayer.getStatus() == Status.PLAYING){
			mediaPlayer.stop();
			
			//enable btnPlay button
			btnPlay.setDisable(false);
			
			//disable btnPause and btnStop
			btnPause.setDisable(true);
			btnStop.setDisable(true);
		}
		
	}//stopSelectecTrack()

	@Override
	public void start(Stage pStage) throws Exception {
		
		//set the title 
		pStage.setTitle("MP3 BlastBox");
		
		//set width and height
		pStage.setWidth(650);
		pStage.setHeight(500);
		
		//create a grid pane 
		GridPane gp = new GridPane(); 
		
		//set gap
		gp.setHgap(10);
		gp.setVgap(10);
		
		//set margin
		gp.setPadding(new Insets(15));
		
		//set labels, list views, buttons and sliders 
		gp.add(lblTracks, 0, 0);
		gp.add(lvAvailableTracks, 0, 1, 2, 12);
		
		gp.add(btnAdd, 3, 4);
		gp.add(btnRemove, 3, 5);
		gp.add(btnRemoveAll, 3, 6);
		gp.add(btnPlay, 3, 7);
		gp.add(btnPause, 3, 8);
		gp.add(btnStop, 3, 9);
		gp.add(lblVolume, 3, 10);
		gp.add(slrVolume, 3, 11);
		
		gp.add(lblPlayList, 4, 0);
		gp.add(lvSelectedTracks, 4, 1, 2, 12);
		
		gp.add(lblStatus, 4, 14);
		gp.add(slrStatus, 4, 15);
		
		//create a scene
		Scene s= new Scene(gp);
		
		//style the scene 
		s.getStylesheets().add("stylesheet.css");
		
		//set the scene
		pStage.setScene(s);

		//set application image on the scene 
		Image applicationIcon = new Image(getClass().getResourceAsStream("icon.png"));
		pStage.getIcons().add(applicationIcon);
		
		//show the stage
		pStage.show();
		
	}//start()
	
	public void stop() {
		
	}//stop()
	
	public static void main (String [] args) {
		launch (); 
	}//main()
	
}//class