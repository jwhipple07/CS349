package photoViewer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;

public class photoAlbum implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1838046152863093165L;
	public List<Photo> photos;
	public Integer photoNum;
	private Photo currentPhoto;

	public photoAlbum() {
		photos = new ArrayList<Photo>();
		photoNum = 0;
	}

	public void incrementNum() {
		if (photoNum < getTotalSize()) {
			photoNum++;
		}
	}

	public Photo getCurrentPhoto() {
		if (currentPhoto == null) {
			currentPhoto = photos.get(0);
		}
		return currentPhoto;
	}

	public void decrementNum() {
		if (photoNum > 1) {
			photoNum--;
		}
	}

	public void nextPhoto() {
		incrementNum();
		setCurrentPhoto();
	}

	public void prevPhoto() {
		decrementNum();
		setCurrentPhoto();
	}

	public ImageIcon getPhoto() {
		return photos.get(photoNum - 1).image;
	}

	public void setCurrentPhoto() {
		if (photoNum == 0) {
			currentPhoto = new Photo();
			return;
		}
		currentPhoto = photos.get(photoNum - 1);
	}

	public void addPhoto(Photo photo) {
		if (photos == null) {
			photos = new ArrayList<Photo>();
		}
		photos.add(photoNum, photo);
		photoNum++;
		setCurrentPhoto();

	}

	public void deletePhoto() {

		photos.remove(photoNum - 1);
		if (photoNum == 1 || photoNum == getTotalSize() + 1) {
			photoNum--;
		}
		setCurrentPhoto();
	}

	public boolean isEmpty() {
		if (photos == null) {
			return true;
		}
		return photos.isEmpty();
	}

	public Integer getTotalSize() {
		return photos.size();
	}

	public Integer getIndex() {
		if (photos.isEmpty()) {
			return 0;
		}
		return photos.indexOf(currentPhoto) + 1;
	}
}
