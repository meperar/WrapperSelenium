package principal;

public class Book {
	private String sitio;
	private String titulo;
	private String  autor;
	private double oldPrice;
	private double newPrice;
	private double descuento;

	public Book(String sitio, String title, String autor, double oldPrice, double newPrice, double descuento) {
		// TODO Auto-generated constructor stub
		this.sitio=sitio;
		this.titulo=title;
		this.autor=autor;
		this.oldPrice=oldPrice;
		this.newPrice=newPrice;
		this.descuento=descuento;
	}

	public String getSitio() {
		return sitio;
	}

	public void setSitio(String sitio) {
		this.sitio = sitio;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getAutor() {
		return autor;
	}

	public void setAutor(String autor) {
		this.autor = autor;
	}

	public double getOldPrice() {
		return oldPrice;
	}

	public void setOldPrice(double oldPrice) {
		this.oldPrice = oldPrice;
	}

	public double getNewPrice() {
		return newPrice;
	}

	public void setNewPrice(double newPrice) {
		this.newPrice = newPrice;
	}

	public double getDescuento() {
		return descuento;
	}

	public void setDescuento(double descuento) {
		this.descuento = descuento;
	}
	
	

}
