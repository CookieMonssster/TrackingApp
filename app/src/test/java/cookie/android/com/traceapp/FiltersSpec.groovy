package cookie.android.com.traceapp

import cookie.android.com.traceapp.helpers.Filters
import org.robolectric.annotation.Config
import pl.polidea.robospock.RoboSpecification

@Config(manifest = "./src/main/AndroidManifest.xml", constants = BuildConfig, sdk = 18)
public class FiltersSpec extends RoboSpecification {

    def "Check GPS filter"() {
        given: "Example list of locations"
            def filters = new Filters(Filters.getFirstLoc());
            def exampleList = filters.getExampleData();
        when: "filter data"
            def filteredData = filters.simpleFilterWithList(exampleList);
        then: "Size is correct"
            filteredData.size() == exampleList.size();
    }

}