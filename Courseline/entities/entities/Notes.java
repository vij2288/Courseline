package entities;

public class Notes {
	
	String name;
	byte[] image;
	
	public Notes(){
		
	}
	
	public Notes(String name, byte[] image) {
		this.name = name;
		this.image = image;

	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

}
