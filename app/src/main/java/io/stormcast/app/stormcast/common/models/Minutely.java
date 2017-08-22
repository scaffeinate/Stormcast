package io.stormcast.app.stormcast.common.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Minutely implements Parcelable {

    public final static Parcelable.Creator<Minutely> CREATOR = new Creator<Minutely>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Minutely createFromParcel(Parcel in) {
            Minutely instance = new Minutely();
            instance.summary = ((String) in.readValue((String.class.getClassLoader())));
            instance.icon = ((String) in.readValue((String.class.getClassLoader())));
            in.readList(instance.data, (io.stormcast.app.stormcast.common.models.Datum.class.getClassLoader()));
            return instance;
        }

        public Minutely[] newArray(int size) {
            return (new Minutely[size]);
        }

    };
    @SerializedName("summary")
    @Expose
    private String summary;
    @SerializedName("icon")
    @Expose
    private String icon;
    @SerializedName("data")
    @Expose
    private List<Datum> data = new ArrayList<Datum>();

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public List<Datum> getData() {
        return data;
    }

    public void setData(List<Datum> data) {
        this.data = data;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(summary);
        dest.writeValue(icon);
        dest.writeList(data);
    }

    public int describeContents() {
        return 0;
    }

}
