/* eslint-disable array-callback-return */
/* eslint-disable react-hooks/exhaustive-deps */
/* eslint-disable no-unused-vars */
import {
    Box,
    Flex,
    IconButton,
    SkeletonText,
    Text,
} from '@chakra-ui/react'
import { FaLocationArrow, FaTimes } from 'react-icons/fa'
import { useLocation } from "react-router-dom"
import {
    useJsApiLoader,
    GoogleMap,
    Marker,
    DirectionsRenderer,
} from '@react-google-maps/api'
import { useEffect, useState } from 'react'
import { API, GMAP_API_KEY } from "../../sharedComponent/API"
import axios from 'axios'


const LiveLocationTracking = () => {
    const { isLoaded } = useJsApiLoader({
        googleMapsApiKey: GMAP_API_KEY,
        libraries: ['places'],
    })
    const { state } = useLocation();

    const [map, setMap] = useState(/** @type google.maps.Map */(null))
    const [directionsResponse, setDirectionsResponse] = useState(null)
    const [center, setCenter] = useState([])
    const [distance, setDistance] = useState('')
    const [duration, setDuration] = useState('')
    const [mutipleMarkers, setMutipleMarkers] = useState([
    ])
    useEffect(() => {
        if (state?.rideId === null) {
            window.history.go(-1)
        }
        const loggedInUserInfo = JSON.parse(
            localStorage.getItem("loggedInUserDetails")
        );
        if (loggedInUserInfo) {
            const config = {
                headers: { Authorization: `Bearer ${loggedInUserInfo.token}` }
            };

            async function calculateRoute(startLoc, endLoc) {

                // eslint-disable-next-line no-undef
                const directionsService = new google.maps.DirectionsService()
                const results = await directionsService.route({
                    origin: startLoc,
                    destination: endLoc,
                    // eslint-disable-next-line no-undef
                    travelMode: google.maps.TravelMode.DRIVING,
                })
                setDirectionsResponse(results)
                setDistance(results.routes[0].legs[0].distance.text)
                setDuration(results.routes[0].legs[0].duration.text)
            }

            axios.get(`${API}/ride/getAllTripMembers/${state?.rideId ? state?.rideId : 2}`, config).then(resp => {
                if (resp.data.success) {
                    const isDriverHere = resp.data.responseObject.find(riders => loggedInUserInfo.user.userId === riders.rideInfo.userId
                    )
                    if (isDriverHere.rideInfo.driver) {
                        const ridersCoordinates = resp.data.responseObject.filter(item => {
                            return !isDriverHere || !item.rideInfo.driver;
                        })
                        setMutipleMarkers(ridersCoordinates)
                    } else {
                        const riderHere = resp.data.responseObject.find(riders => riders.rideInfo.driver
                        )
                        setCenter({ lat: riderHere.pickupLocation.lattitude, lng: riderHere.pickupLocation.longitude })
                    }
                    resp.data.responseObject.map(rideDetails => {
                        if (loggedInUserInfo.user.userId === rideDetails.rideInfo.userId) {
                            calculateRoute(rideDetails.location1.address, rideDetails.location2.address)
                        }
                    })
                }
            }).catch(err => {
                console.log("err in liveloctrack", err);
            })

            const options = {
                enableHighAccuracy: true,
                timeout: 5000,
                maximumAge: 0,
            };

            function success(pos) {
                const crd = pos.coords;
                setCenter({ lat: crd.latitude, lng: crd.longitude })
            }

            function error(err) {
                console.warn(`ERROR(${err.code}): ${err.message}`);
            }

            navigator.geolocation.getCurrentPosition(success, error, options);
        }
    }, [])

    if (!isLoaded) {
        return <SkeletonText />
    }

    return (
        <Flex
            position='relative'
            flexDirection='column'
            alignItems='center'
            h='100vh'
            w='100vw'
        >
            <Box position='absolute' left={0} top={0} h='100%' w='100%'>
                <GoogleMap
                    center={center}
                    zoom={15}
                    mapContainerStyle={{ width: '100%', height: '100%' }}
                    options={{
                        zoomControl: false,
                        streetViewControl: false,
                        mapTypeControl: false,
                        fullscreenControl: false,
                    }}
                    onLoad={map => setMap(map)}
                >

                    <Marker position={center} icon={{
                        url: (require('../../assets/favicon.ico')),
                        fillColor: '#EB00FF',
                        rotation: 180,
                        scale: 7
                    }} />
                    {directionsResponse && (
                        <DirectionsRenderer directions={directionsResponse} />
                    )}
                    {mutipleMarkers.map((marker, index) => (
                        <Marker key={index} position={{ lat: marker.location1.lattitude, lng: marker.location1.longitude }} />
                    ))}
                </GoogleMap>
            </Box>

            <Box position="absolute" top="2" left="2" p="3" className='mapBackground'>
                <Text fontWeight="medium">Distance: {distance} </Text>
            </Box>

            <Box position="absolute" top="2" right="2" p="3" className='mapBackground'>
                <Text fontWeight="medium">Duration: {duration} </Text>
            </Box>

            <IconButton
                position="absolute"
                bottom="1"
                right="1"
                aria-label='center back'
                backgroundColor="lightblue"
                icon={<FaLocationArrow />}
                isRound
                onClick={() => {
                    map.panTo(center)
                    map.setZoom(15)
                }}
            />
        </Flex>
    )
}

export default LiveLocationTracking