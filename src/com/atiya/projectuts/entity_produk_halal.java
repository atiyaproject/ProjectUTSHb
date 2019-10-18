package com.atiya.projectuts;

public class entity_produk_halal {
		   
		  String nomor_sertifikat; 
		  String nama_produk; 
		  String nama_brand; 
		  String masa_berlaku; 
		   
		  public String getId() { 
		    return nomor_sertifikat; 
		  } 
		 
		  public void setId(String nomor_sertifikat) { 
		    this.nomor_sertifikat = nomor_sertifikat; 
		  } 
		 
		  public String getName() { 
		    return nama_produk; 
		  } 
		 
		  public void setName(String nama_produk) { 
		    this.nama_produk = nama_produk; 
		  } 
		 
		  public String getBrand() { 
		    return nama_brand; 
		  } 
		  
		  public void setBrand(String nama_brand) { 
		    this.nama_brand = nama_brand; 
		  } 
		 
		  public String getMasaberlaku() { 
		    return masa_berlaku; 
		  } 
		 
		  public void setMasaberlaku(String masa_berlaku) { 
		    this.masa_berlaku = masa_berlaku; 
		  }
}
