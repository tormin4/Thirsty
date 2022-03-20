package com.example.thirsty

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.thirsty.databinding.ActivityMapsBinding
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PointOfInterest
import java.io.IOException
import java.io.InputStream

class MapsActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnPoiClickListener {

    private lateinit var map: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    private lateinit var markers: ArrayList<Marker>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Winnipeg, Manitoba.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap

        map.setOnPoiClickListener(this)

        val options = GoogleMapOptions()
        options.mapType(GoogleMap.MAP_TYPE_SATELLITE)
            .compassEnabled(false)
            .rotateGesturesEnabled(false)
            .tiltGesturesEnabled(false)

        // Add a marker in Sydney and move the camera
        val winnipeg = LatLng(49.89, -97.14)
        map.addMarker(MarkerOptions().position(winnipeg).title("Marker in Wpg"))

        cameraConfig()
    }

    private fun cameraConfig(){
        map.setMinZoomPreference(8.0f)
        map.setMaxZoomPreference(15.0f)


        val winnipegBounds = LatLngBounds(
            LatLng((49.718), -97.409),  // SW bounds
            LatLng((50.073), -96.976)   // NE bounds
        )
        map.setLatLngBoundsForCameraTarget(winnipegBounds)
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(winnipegBounds.center, 10f))
    }

    override fun onPoiClick(poi: PointOfInterest) {
        Toast.makeText(this, """Clicked: ${poi.name}
            Place ID:${poi.placeId}
            Latitude:${poi.latLng.latitude} Longitude:${poi.latLng.longitude}""",
            Toast.LENGTH_SHORT
        ).show()
    }

    //loads jsonfile as string
    fun loadJSONFromAsset(context: Context): String? {
        var json: String? = null
        json = try {
            val inpStr = context.assets.open("file_name.json")
            val size = inpStr.available()
            val buffer = ByteArray(size)
            inpStr.read(buffer)
            inpStr.close()
            String(buffer)
        } catch (ex: IOException) {
            ex.printStackTrace()
            return null
        }
        return json
    }

    // returns list of markers
    fun getMarkerList(fileStr : String) : ArrayList<Marker> {
        return parse(loadJSONFromAsset(this)!!)
    }
}