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
        data.add(newLocation(51.75156671, 19.46950998));
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

    public static List<Location> getExampleData3() {
        List<Location> data = new ArrayList<>();
        data.add(newLocation(19.4874619, 51.7594656));
        data.add(newLocation(19.4874552, 51.7594567));
        data.add(newLocation(19.4874637, 51.7594535));
        data.add(newLocation(19.4874665, 51.759431));
        data.add(newLocation(19.4874744, 51.7594169));
        data.add(newLocation(19.487487, 51.7593981));
        data.add(newLocation(19.4874975, 51.7593877));
        data.add(newLocation(19.4875119, 51.7593731));
        data.add(newLocation(19.4875195, 51.7593554));
        data.add(newLocation(19.4875364, 51.7593396));
        data.add(newLocation(19.4875486, 51.7593247));
        data.add(newLocation(19.4875664, 51.7593093));
        data.add(newLocation(19.487566, 51.7592941));
        data.add(newLocation(19.4875772, 51.7592812));
        data.add(newLocation(19.4875732, 51.7592677));
        data.add(newLocation(19.4875863, 51.7592573));
        data.add(newLocation(19.4875872, 51.7592383));
        data.add(newLocation(19.4876073, 51.7592279));
        data.add(newLocation(19.4876206, 51.7592118));
        data.add(newLocation(19.4876278, 51.7592008));
        data.add(newLocation(19.4876484, 51.7591864));
        data.add(newLocation(19.4876704, 51.759161));
        data.add(newLocation(19.4876705, 51.7591568));
        data.add(newLocation(19.4876885, 51.7591536));
        data.add(newLocation(19.4876999, 51.7591334));
        data.add(newLocation(19.4877095, 51.7591223));
        data.add(newLocation(19.4877125, 51.7591054));
        data.add(newLocation(19.4877239, 51.7590931));
        data.add(newLocation(19.4877385, 51.7590847));
        data.add(newLocation(19.4877456, 51.7590738));
        data.add(newLocation(19.4877532, 51.7590521));
        data.add(newLocation(19.4877649, 51.7590368));
        data.add(newLocation(19.4877856, 51.7590209));
        data.add(newLocation(19.4877966, 51.7590136));
        data.add(newLocation(19.4878147, 51.7589918));
        data.add(newLocation(19.4878126, 51.7589846));
        data.add(newLocation(19.4878189, 51.758978));
        data.add(newLocation(19.4878451, 51.758975));
        data.add(newLocation(19.487858, 51.7589552));
        data.add(newLocation(19.487863, 51.7589326));
        data.add(newLocation(19.487868, 51.7589122));
        data.add(newLocation(19.4878649, 51.7588941));
        data.add(newLocation(19.4878929, 51.758876));
        data.add(newLocation(19.487892, 51.7588638));
        data.add(newLocation(19.4879002, 51.7588508));
        data.add(newLocation(19.4879054, 51.7588438));
        data.add(newLocation(19.4879001, 51.7588296));
        data.add(newLocation(19.4879025, 51.7588101));
        data.add(newLocation(19.4879104, 51.7587944));
        data.add(newLocation(19.4879324, 51.7587967));
        data.add(newLocation(19.4879399, 51.7587922));
        data.add(newLocation(19.4879553, 51.7587856));
        data.add(newLocation(19.4879641, 51.7587781));
        data.add(newLocation(19.4879757, 51.7587714));
        data.add(newLocation(19.4879835, 51.7587616));
        data.add(newLocation(19.4879871, 51.7587492));
        data.add(newLocation(19.4879887, 51.7587393));
        data.add(newLocation(19.4879974, 51.7587303));
        data.add(newLocation(19.4880095, 51.7587204));
        data.add(newLocation(19.4880165, 51.7587083));
        data.add(newLocation(19.4880227, 51.7586956));
        data.add(newLocation(19.4880267, 51.7586796));
        data.add(newLocation(19.4880379, 51.7586684));
        data.add(newLocation(19.4880454, 51.7586559));
        data.add(newLocation(19.4880484, 51.7586445));
        data.add(newLocation(19.4880581, 51.7586309));
        data.add(newLocation(19.4880639, 51.7586179));
        data.add(newLocation(19.4880765, 51.7586032));
        data.add(newLocation(19.4880918, 51.7585964));
        data.add(newLocation(19.4881091, 51.758598));
        data.add(newLocation(19.4881216, 51.7586051));
        data.add(newLocation(19.4881207, 51.7586142));
        data.add(newLocation(19.4881086, 51.7586249));
        data.add(newLocation(19.4880933, 51.7586388));
        data.add(newLocation(19.4880862, 51.7586494));
        data.add(newLocation(19.4880802, 51.7586614));
        data.add(newLocation(19.4880724, 51.7586755));
        data.add(newLocation(19.4880735, 51.7586873));
        data.add(newLocation(19.4880728, 51.7586999));
        data.add(newLocation(19.4880787, 51.7587102));
        data.add(newLocation(19.4880732, 51.7587197));
        data.add(newLocation(19.4880724, 51.7587277));
        data.add(newLocation(19.4880673, 51.7587383));
        data.add(newLocation(19.4880514, 51.7587478));
        data.add(newLocation(19.488037, 51.758756));
        data.add(newLocation(19.4880302, 51.7587673));
        data.add(newLocation(19.4880205, 51.7587778));
        data.add(newLocation(19.488012, 51.7587863));
        data.add(newLocation(19.4879974, 51.7587911));
        data.add(newLocation(19.4879877, 51.7588017));
        data.add(newLocation(19.4879685, 51.7588104));
        data.add(newLocation(19.4879582, 51.7588241));
        data.add(newLocation(19.4879421, 51.758835));
        data.add(newLocation(19.4879268, 51.7588479));
        data.add(newLocation(19.4879108, 51.7588492));
        data.add(newLocation(19.4879097, 51.7588579));
        data.add(newLocation(19.4879011, 51.7588696));
        data.add(newLocation(19.4878869, 51.7588801));
        data.add(newLocation(19.4878926, 51.7588896));
        data.add(newLocation(19.4878743, 51.7588938));
        data.add(newLocation(19.4878635, 51.7589091));
        data.add(newLocation(19.4878489, 51.7589246));
        data.add(newLocation(19.4878478, 51.7589324));
        data.add(newLocation(19.4878555, 51.7589428));
        data.add(newLocation(19.4878521, 51.7589554));
        data.add(newLocation(19.487853, 51.7589686));
        data.add(newLocation(19.4878384, 51.758984));
        data.add(newLocation(19.487833, 51.7589964));
        data.add(newLocation(19.4878348, 51.7590037));
        data.add(newLocation(19.4878468, 51.7590104));
        data.add(newLocation(19.4878441, 51.7590232));
        data.add(newLocation(19.4878285, 51.7590344));
        data.add(newLocation(19.4878302, 51.7590417));
        data.add(newLocation(19.4878243, 51.7590545));
        data.add(newLocation(19.4878112, 51.7590665));
        data.add(newLocation(19.4878003, 51.7590775));
        data.add(newLocation(19.4877846, 51.7590861));
        data.add(newLocation(19.4877737, 51.7591009));
        data.add(newLocation(19.4877646, 51.7591125));
        data.add(newLocation(19.4877559, 51.7591307));
        data.add(newLocation(19.4877417, 51.7591469));
        data.add(newLocation(19.4877271, 51.7591608));
        data.add(newLocation(19.4877161, 51.7591707));
        data.add(newLocation(19.4877299, 51.7592004));
        data.add(newLocation(19.4877172, 51.7592132));
        data.add(newLocation(19.4877001, 51.7592227));
        data.add(newLocation(19.4876934, 51.7592364));
        data.add(newLocation(19.4876834, 51.7592483));
        data.add(newLocation(19.4876834, 51.7592573));
        data.add(newLocation(19.4876608, 51.759266));
        data.add(newLocation(19.4876357, 51.7592747));
        data.add(newLocation(19.4876261, 51.7592908));
        data.add(newLocation(19.4876121, 51.759302));
        data.add(newLocation(19.4876042, 51.7593158));
        data.add(newLocation(19.4875872, 51.7593294));
        data.add(newLocation(19.487573, 51.759338));
        data.add(newLocation(19.4875658, 51.7593444));
        data.add(newLocation(19.4875566, 51.7593465));
        data.add(newLocation(19.4875477, 51.7593588));
        data.add(newLocation(19.4875356, 51.7593728));
        data.add(newLocation(19.4875166, 51.7593884));
        data.add(newLocation(19.4875002, 51.7593933));
        data.add(newLocation(19.4874881, 51.7594071));
        data.add(newLocation(19.4874761, 51.7594248));
        data.add(newLocation(19.487473, 51.7594284));
        data.add(newLocation(19.4874636, 51.7594411));
        data.add(newLocation(19.4874454, 51.759455));
        data.add(newLocation(19.4874333, 51.7594718));
        data.add(newLocation(19.4874197, 51.7594796));
        return data;
    }

    public static Location getFirstLoc() {
        return newLocation(51.757490, 19.463614);

    }

    public List<Location> simpleFilterWithList(List<Location> list) {
        List<Location> filteredList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
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
