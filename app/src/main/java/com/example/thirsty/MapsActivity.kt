package com.example.thirsty

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.thirsty.databinding.ActivityMapsBinding
import com.google.android.gms.common.api.Status
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.*
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.model.RectangularBounds
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.AutocompleteActivity
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener


class MapsActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnPoiClickListener  {

    private lateinit var map: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    private lateinit var marker: Marker
    private lateinit var qButton: Button
    private var TAG = "Info"



    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        autoCompleteSearch()


        qButton = findViewById<Button>(R.id.buttonquenched)
        qButton.setOnClickListener{
            val frontint = Intent(this,FrontActivity::class.java)
            startActivity(frontint)
        }
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
        options.mapType(GoogleMap.MAP_TYPE_NORMAL)
            .compassEnabled(false)
            .rotateGesturesEnabled(false)
            .tiltGesturesEnabled(false)

        // Add a marker in Sydney and move the camera
        val winnipeg = LatLng(49.884444, -97.146389)
        marker = map.addMarker(MarkerOptions().position(winnipeg).title("Destination"))!!
        marker.setVisible(false);

        cameraConfig()
    }

    private fun cameraConfig(){
        map.setMinZoomPreference(8.0f)
        map.setMaxZoomPreference(20.0f)

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

    private fun autoCompleteSearch(){
        //Initialize the SDK and create a new Places Client instance
        if(!Places.isInitialized()) {
            Places.initialize(applicationContext, "AIzaSyBN2RH2u8Hs-t47sTYnFQSc7_rPZwsxQXo");
        }

        // Initialize the placesClient
        val placesClient = Places.createClient(this)

        // Initialize the AutocompleteSupportFragment.
        val autocompleteFragment =
            supportFragmentManager.findFragmentById(R.id.autocomplete_fragment)
                    as? AutocompleteSupportFragment

        // Setting location bias to Winnipeg
        autocompleteFragment?.setLocationBias(RectangularBounds.newInstance(
            LatLng(49.884444, -97.146389),
            LatLng(49.884444, -97.146389)
        ))

        //Selecting a country
        autocompleteFragment?.setCountries("CA")

        // Specify the types of place data to return.
        autocompleteFragment?.setPlaceFields(listOf(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG))

        // Set up a PlaceSelectionListener to handle the response.
        autocompleteFragment?.setOnPlaceSelectedListener(object : PlaceSelectionListener {
            override fun onPlaceSelected(place: Place) {
                // TODO: Get info about the selected place.
                val latiLongi = place.latLng
                map.animateCamera(CameraUpdateFactory.newLatLngZoom(latiLongi!!, 13.0f))
                marker.remove()
                marker.setVisible(true)
                marker = map.addMarker(MarkerOptions().position(latiLongi).title("Destination"))!!
                Log.i(TAG, "Place: ${place.name}, ${place.id}")
            }
            override fun onError(status: Status) {
                // TODO: Handle the error.
                Log.i(TAG, "An error occurred: $status")
            }
        })
    }

}

