package com.example.servingcalculator;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(indices = {@Index(value="nume",unique = true)})
public class AteFood implements Parcelable {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "nume")
    private String nume;
    @ColumnInfo(name = "valoare_energetica")
    private double valoareEnergetica;
    @ColumnInfo(name = "grasimi")
    private double grasimi;
    @ColumnInfo(name = "acizi")
    private double acizi;
    @ColumnInfo(name = "glucide")
    private double glucide;
    @ColumnInfo(name = "zaharuri")
    private double zaharuri;
    @ColumnInfo(name = "fibre")
    private double fibre;
    @ColumnInfo(name = "proteine")
    private double proteine;
    @ColumnInfo(name = "sare")
    private double sare;

    protected AteFood(Parcel in) {
        nume = in.readString();
        valoareEnergetica = in.readDouble();
        grasimi = in.readDouble();
        acizi = in.readDouble();
        glucide = in.readDouble();
        zaharuri = in.readDouble();
        fibre = in.readDouble();
        proteine = in.readDouble();
        sare = in.readDouble();
    }
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(nume);
        dest.writeDouble(valoareEnergetica);
        dest.writeDouble(grasimi);
        dest.writeDouble(acizi);
        dest.writeDouble(glucide);
        dest.writeDouble(zaharuri);
        dest.writeDouble(fibre);
        dest.writeDouble(proteine);
        dest.writeDouble(sare);
    }
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<AteFood> CREATOR = new Parcelable.Creator<AteFood>() {
        @Override
        public AteFood createFromParcel(Parcel in) {
            return new AteFood(in);
        }

        @Override
        public AteFood[] newArray(int size) {
            return new AteFood[size];
        }
    };
    @Override
    protected Object clone() {
        AteFood clonedFood = new AteFood();
        clonedFood.nume = this.nume;
        clonedFood.valoareEnergetica = this.valoareEnergetica;
        clonedFood.grasimi = this.grasimi;
        clonedFood.acizi = this.acizi;
        clonedFood.glucide = this.glucide;
        clonedFood.zaharuri = this.zaharuri;
        clonedFood.fibre = this.fibre;
        clonedFood.proteine = this.proteine;
        clonedFood.sare = this.sare;

        return clonedFood;
    }
    public AteFood() {
    }

    public AteFood(String nume, double valoareEnergetica, double grasimi, double acizi, double glucide, double zaharuri, double fibre, double proteine, double sare) {
        this.nume = nume;
        this.valoareEnergetica = valoareEnergetica;
        this.grasimi = grasimi;
        this.acizi = acizi;
        this.glucide = glucide;
        this.zaharuri = zaharuri;
        this.fibre = fibre;
        this.proteine = proteine;
        this.sare = sare;
    }
    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public double getValoareEnergetica() {
        return valoareEnergetica;
    }

    public void setValoareEnergetica(double valoareEnergetica) {
        this.valoareEnergetica = valoareEnergetica;
    }

    public double getGrasimi() {
        return grasimi;
    }

    public void setGrasimi(double grasimi) {
        this.grasimi = grasimi;
    }

    public double getAcizi() {
        return acizi;
    }

    public void setAcizi(double acizi) {
        this.acizi = acizi;
    }

    public double getGlucide() {
        return glucide;
    }

    public void setGlucide(double glucide) {
        this.glucide = glucide;
    }

    public double getZaharuri() {
        return zaharuri;
    }

    public void setZaharuri(double zaharuri) {
        this.zaharuri = zaharuri;
    }

    public double getFibre() {
        return fibre;
    }

    public void setFibre(double fibre) {
        this.fibre = fibre;
    }

    public double getProteine() {
        return proteine;
    }

    public void setProteine(double proteine) {
        this.proteine = proteine;
    }

    public double getSare() {
        return sare;
    }

    public void setSare(double sare) {
        this.sare = sare;
    }

    @Override
    public String toString() {
        return "AteFood{" +
                "nume=" + nume +
                ", valoareEnergetica=" + valoareEnergetica +
                ", grasimi=" + grasimi +
                ", acizi=" + acizi +
                ", glucide=" + glucide +
                ", zaharuri=" + zaharuri +
                ", fibre=" + fibre +
                ", proteine=" + proteine +
                ", sare=" + sare +
                '}';
    }
}
