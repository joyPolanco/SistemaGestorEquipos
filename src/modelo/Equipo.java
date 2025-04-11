package modelo;

public abstract class Equipo {
	private int id;
    private String descripcion;
    private String marca;
    private String modelo;
    private String serie;
    private String marbete;
    private String accesorios;

    public Equipo(int id, String serie,String modelo ,String marca, String descripcion , String marbete, String accesorios) {
        this.id=id;
    	this.descripcion = descripcion;
        this.marca = marca;
        this.modelo = modelo;
        this.serie = serie;
        this.marbete = marbete;
        this.accesorios = accesorios;
      
    }

    public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}





    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getSerie() {
        return serie;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }

    public String getMarbete() {
        return marbete;
    }

    public void setMarbete(String marbete) {
        this.marbete = marbete;
    }

    public String getAccesorios() {
        return accesorios;
    }

    public void setAccesorios(String accesorios) {
        this.accesorios = accesorios;
    }
    
}
  