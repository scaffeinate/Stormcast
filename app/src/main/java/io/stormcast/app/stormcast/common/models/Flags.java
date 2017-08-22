package io.stormcast.app.stormcast.common.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Flags implements Parcelable {

    public final static Parcelable.Creator<Flags> CREATOR = new Creator<Flags>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Flags createFromParcel(Parcel in) {
            Flags instance = new Flags();
            in.readList(instance.sources, (java.lang.String.class.getClassLoader()));
            in.readList(instance.isdStations, (java.lang.String.class.getClassLoader()));
            instance.units = ((String) in.readValue((String.class.getClassLoader())));
            return instance;
        }

        public Flags[] newArray(int size) {
            return (new Flags[size]);
        }

    };
    @SerializedName("sources")
    @Expose

    private List<String> sources = new ArrayList<String>();
    @SerializedName("isd-stations")
    @Expose

    private List<String> isdStations = new ArrayList<String>();
    @SerializedName("units")
    @Expose
    private String units;

    public List<String> getSources() {
        return sources;
    }

    public void setSources(List<String> sources) {
        this.sources = sources;
    }

    public List<String> getIsdStations() {
        return isdStations;
    }

    public void setIsdStations(List<String> isdStations) {
        this.isdStations = isdStations;
    }

    public String getUnits() {
        return units;
    }

    public void setUnits(String units) {
        this.units = units;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(sources);
        dest.writeList(isdStations);
        dest.writeValue(units);
    }

    public int describeContents() {
        return 0;
    }

}
