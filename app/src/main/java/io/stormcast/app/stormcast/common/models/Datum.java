
package io.stormcast.app.stormcast.common.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum implements Parcelable
{

    @SerializedName("time")
    @Expose
    private int time;
    @SerializedName("precipIntensity")
    @Expose
    private int precipIntensity;
    @SerializedName("precipProbability")
    @Expose
    private int precipProbability;
    public final static Parcelable.Creator<Datum> CREATOR = new Creator<Datum>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Datum createFromParcel(Parcel in) {
            Datum instance = new Datum();
            instance.time = ((int) in.readValue((int.class.getClassLoader())));
            instance.precipIntensity = ((int) in.readValue((int.class.getClassLoader())));
            instance.precipProbability = ((int) in.readValue((int.class.getClassLoader())));
            return instance;
        }

        public Datum[] newArray(int size) {
            return (new Datum[size]);
        }

    }
    ;

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getPrecipIntensity() {
        return precipIntensity;
    }

    public void setPrecipIntensity(int precipIntensity) {
        this.precipIntensity = precipIntensity;
    }

    public int getPrecipProbability() {
        return precipProbability;
    }

    public void setPrecipProbability(int precipProbability) {
        this.precipProbability = precipProbability;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(time);
        dest.writeValue(precipIntensity);
        dest.writeValue(precipProbability);
    }

    public int describeContents() {
        return  0;
    }

}
