
package io.stormcast.app.stormcast.common.models;

import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Hourly implements Parcelable
{

    @SerializedName("summary")
    @Expose
    private String summary;
    @SerializedName("icon")
    @Expose
    private String icon;
    @SerializedName("data")
    @Expose
    @Valid
    private List<Datum_> data = new ArrayList<Datum_>();
    public final static Parcelable.Creator<Hourly> CREATOR = new Creator<Hourly>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Hourly createFromParcel(Parcel in) {
            Hourly instance = new Hourly();
            instance.summary = ((String) in.readValue((String.class.getClassLoader())));
            instance.icon = ((String) in.readValue((String.class.getClassLoader())));
            in.readList(instance.data, (io.stormcast.app.stormcast.common.models.Datum_.class.getClassLoader()));
            return instance;
        }

        public Hourly[] newArray(int size) {
            return (new Hourly[size]);
        }

    }
    ;

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

    public List<Datum_> getData() {
        return data;
    }

    public void setData(List<Datum_> data) {
        this.data = data;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(summary);
        dest.writeValue(icon);
        dest.writeList(data);
    }

    public int describeContents() {
        return  0;
    }

}