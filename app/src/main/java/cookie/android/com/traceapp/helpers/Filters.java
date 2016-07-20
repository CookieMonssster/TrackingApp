package cookie.android.com.traceapp.helpers;

import android.location.Location;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Copyright (C) CookieStudio, 2016
 * All rights reserved.
 */
public class Filters {

    private static final long WILDLY_OUT = 15;
    private static final long TOO_OLD = 10000;
    private static final long NO_SAMPLES = 3;

    long lastLocationTime;

    double oldLon;
    double oldLat;

    public Filters(Location startLocation) {
        oldLon = startLocation.getLongitude();
        oldLat = startLocation.getLatitude();
    }

    public void setOldLoc(Location loc) {
        oldLon = loc.getLongitude();
        oldLat = loc.getLatitude();
    }

    public Location simpleFilter(Location loc) {
        double filteredLon = oldLon;
        double filteredLat = oldLat;

        if (loc.getAccuracy() < WILDLY_OUT) {
            filteredLon = (NO_SAMPLES * oldLon + loc.getLongitude()) / (NO_SAMPLES + 1);
            filteredLat = (NO_SAMPLES * oldLat + loc.getLatitude()) / (NO_SAMPLES + 1);
            lastLocationTime = System.currentTimeMillis() - lastLocationTime;
        }

        if (lastLocationTime > TOO_OLD) {
            filteredLon = loc.getLongitude();
            filteredLat = loc.getLatitude();
        }

        oldLon = filteredLon;
        oldLat = filteredLat;
        Location filteredLoc = new Location("");
        filteredLoc.setLatitude(filteredLat);
        filteredLoc.setLongitude(filteredLon);
        return filteredLoc;
    }

    private static Location newLocation(double latitude, double longitude) {
        Location loc = new Location("");
        loc.setLatitude(latitude);
        loc.setLongitude(longitude);
        return loc;
    }

    public static List<Location> getExampleData() {
        List<Location> data = new ArrayList<>();

        data.add(newLocation(51.757616, 19.463551));
        data.add(newLocation(51.757745, 19.463524));
        data.add(newLocation(51.757881, 19.463474));
        data.add(newLocation(51.757981, 19.463560));
        data.add(newLocation(51.758063, 19.463490));
        data.add(newLocation(51.758166, 19.463425));
        data.add(newLocation(51.758289, 19.463343));
        data.add(newLocation(51.758409, 19.463285));
        data.add(newLocation(51.758534, 19.463286));
        data.add(newLocation(51.758632, 19.463250));
        data.add(newLocation(51.758736, 19.463201));
        data.add(newLocation(51.758821, 19.463121));
        data.add(newLocation(51.758956, 19.463005));
        data.add(newLocation(51.759066, 19.462952));
        data.add(newLocation(51.759228, 19.462927));
        data.add(newLocation(51.759346, 19.462922));
        data.add(newLocation(51.759419, 19.462760));
        data.add(newLocation(51.759448, 19.462619));
        data.add(newLocation(51.759545, 19.462682));
        data.add(newLocation(51.759707, 19.462625));
        data.add(newLocation(51.759833, 19.462524));
        data.add(newLocation(51.759898, 19.462415));
        data.add(newLocation(51.759950, 19.462240));
        data.add(newLocation(51.759976, 19.462007));
        data.add(newLocation(51.759953, 19.461787));
        data.add(newLocation(51.759919, 19.461521));
        data.add(newLocation(51.759877, 19.461332));

        return data;
    }

    public static List<Location> getExampleData2() {
        List<Location> data = new ArrayList<>();
        data.add(newLocation(51.757616, 19.463551));
        data.add(newLocation(51.757745, 19.463524));
        data.add(newLocation(51.757881, 19.463474));
        data.add(newLocation(51.757981, 19.46356));
        data.add(newLocation(51.758063, 19.46349));
        data.add(newLocation(51.758166, 19.463425));
        data.add(newLocation(51.758289, 19.463343));
        data.add(newLocation(51.758409, 19.463285));
        data.add(newLocation(51.758534, 19.463286));
        data.add(newLocation(51.758632, 19.46325));
        data.add(newLocation(51.758736, 19.463201));
        data.add(newLocation(51.758821, 19.463121));
        data.add(newLocation(51.758956, 19.463005));
        data.add(newLocation(51.759066, 19.462952));
        data.add(newLocation(51.759228, 19.462927));
        data.add(newLocation(51.759346, 19.462922));
        data.add(newLocation(51.759419, 19.46276));
        data.add(newLocation(51.759448, 19.462619));
        data.add(newLocation(51.759545, 19.462682));
        data.add(newLocation(51.759707, 19.462625));
        data.add(newLocation(51.759833, 19.462524));
        data.add(newLocation(51.759898, 19.462415));
        data.add(newLocation(51.759950, 19.46224));
        data.add(newLocation(51.759976, 19.462007));
        data.add(newLocation(51.759953, 19.461787));
        data.add(newLocation(51.759919, 19.461521));
        data.add(newLocation(51.759877, 19.461332));
        data.add(newLocation(51.752540, 19.46813499));
        data.add(newLocation(51.752563, 19.46829927));
        data.add(newLocation(51.752512, 19.46842246));
        data.add(newLocation(51.75263346, 19.46860935));
        data.add(newLocation(51.75272011, 19.46879591));
        data.add(newLocation(51.75257148, 19.46892445));
        data.add(newLocation(51.75247842, 19.46895399));
        data.add(newLocation(51.75232191, 19.46900419));
        data.add(newLocation(51.75220341, 19.46909206));
        data.add(newLocation(51.75206916, 19.46921073));
        data.add(newLocation(51.75198595, 19.469272));
        data.add(newLocation(51.75186588, 19.46937021));
        data.add(newLocation(51.75176669, 19.46937202));
        data.add(newLocation(51.75165468, 19.4694416));
        data.add(newLocation(51.75156671, 19.46950998 ));
        data.add(newLocation(51.75143384, 19.46958946));
        data.add(newLocation(51.75131821, 19.46965297));
        data.add(newLocation(51.75120649, 19.46976786));
        data.add(newLocation(51.75109969, 19.46989523));
        data.add(newLocation(51.75094989, 19.46999943));
        data.add(newLocation(51.75082779, 19.47005549));
        data.add(newLocation(51.75084966, 19.47024476));
        data.add(newLocation(51.75091246, 19.47042681));
        data.add(newLocation(51.75095349, 19.47061922));
        data.add(newLocation(51.75095353, 19.47077571));
        data.add(newLocation(51.75096722, 19.47095628));
        data.add(newLocation(51.75102670, 19.47120368));
        data.add(newLocation(51.75106621, 19.47135646));
        data.add(newLocation(51.75109859, 19.4715272));
        data.add(newLocation(51.75116008, 19.47172915));
        data.add(newLocation(51.75119833, 19.47198319));
        data.add(newLocation(51.75122257, 19.47212864));
        data.add(newLocation(51.75128728, 19.47223751));
        data.add(newLocation(51.75138137, 19.47249409));
        data.add(newLocation(51.75141825, 19.47269991));
        data.add(newLocation(51.75144420, 19.4729615));
        data.add(newLocation(51.75140397, 19.47310484));
        data.add(newLocation(51.75129829, 19.47314465));
        data.add(newLocation(51.75114473, 19.47320984));
        data.add(newLocation(51.75103731, 19.47326194));
        data.add(newLocation(51.75091694, 19.47333008));
        data.add(newLocation(51.75077842, 19.47345547));
        return data;
    }

    public static Location getFirstLoc() {
        return newLocation(51.757490, 19.463614);

    }

    public List<Location> simpleFilterWithList(List<Location> list) {
        List<Location> filteredList = new ArrayList<>();
        for(int i = 0; i < list.size(); i++) {
            filteredList.add(simpleFilter(list.get(i)));
            printLocation(list.get(i), filteredList.get(i));
        }
        return filteredList;
    }

    private void printLocation(Location loc, Location filteredLoc) {
        Log.d("Loc", loc.getLatitude() + " " + loc.getLongitude() + " "
                + filteredLoc.getLatitude() + " " + filteredLoc.getLongitude());
    }
}
