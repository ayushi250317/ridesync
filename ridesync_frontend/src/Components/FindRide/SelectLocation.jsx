/* eslint-disable no-unused-vars */
import { Box, Button, Center, Flex, Input, Text } from '@chakra-ui/react'
import { HiDotsVertical } from "react-icons/hi";
import React, { useState } from 'react'
import { ImLocation2 } from "react-icons/im";
import { FaRegCircle } from "react-icons/fa6";
import { CgArrowsExchangeV } from "react-icons/cg";
// import axios from 'axios';
import GooglePlacesAutocomplete from 'react-google-places-autocomplete';
import { geocodeByPlaceId } from 'react-google-places-autocomplete';
const SelectLocation = () => {

    // axios.get('https://maps.googleapis.com/maps/api/place/autocomplete/json?input=dal&key=AIzaSyCys3GCN2NpN5LzPXeQYR6hWxq6lVG0BJI&radius=5000&location=44.637673,-63.596547').then(resp => console.log({ data: resp.data }))

    const [fromvalue, setFromValue] = useState(null);
    const [tovalue, setToValue] = useState(null);
    const [temp, setTemp] = useState(null);
    const [fromLatLng, setFromLatLng] = useState({})
    const [toLatLng, setToLatLng] = useState({})


    const handleSubmit = () => {
        geocodeByPlaceId(fromvalue.value.place_id)
            .then(results => { setFromLatLng({ lat: results[0].geometry.location.lat(), lng: results[0].geometry.location.lng() }) })
            .catch(error => console.error(error));
        geocodeByPlaceId(tovalue.value.place_id)
            .then(results => { setToLatLng({ lat: results[0].geometry.location.lat(), lng: results[0].geometry.location.lng() }) })
            .catch(error => console.error(error));
    }
    console.log({ from: fromLatLng, to: toLatLng });
    const handleExchange = () => {
    }


    return (
        <Box w="100%">
            <br />
            <Text fontSize="2xl" textAlign="center"> Find Rides</Text>
            <br />
            <Center flexDir="column" >
                <Flex justifyContent="center" alignItems="center" w={["90%", "95%", "50%", "50%"]}>
                    <FaRegCircle size="20px" color='blue' className='mx-2' />
                    {/* <Input placeholder='From' ml="4"></Input> */}
                    <Box w="100%">

                        <GooglePlacesAutocomplete apiKey='AIzaSyCys3GCN2NpN5LzPXeQYR6hWxq6lVG0BJI' debounce={400}
                            selectProps={{
                                placeholder: "From",
                                value: fromvalue,
                                onChange: setFromValue,
                            }}

                        />
                    </Box>

                    <HiDotsVertical size="25px" />
                </Flex>
                <br />
                <Flex justifyContent="center" alignItems="center" w={["90%", "95%", "50%", "50%"]}>
                    <ImLocation2 size="30px" color='green' className='mx-1' />
                    {/* <Input placeholder='To' ml="4"></Input> */}
                    <Box w="100%">

                        <GooglePlacesAutocomplete apiKey='AIzaSyCys3GCN2NpN5LzPXeQYR6hWxq6lVG0BJI' debounce={400}
                            onPress={(data, details = null) => {
                                // setToValue({searchKeyword: data.description})
                                console.log("data", data);
                            }}
                            selectProps={{
                                placeholder: "To",
                                value: tovalue,
                                onChange: setToValue,
                            }} />
                    </Box>
                    <CgArrowsExchangeV size="25px" onClick={handleExchange} />
                </Flex>
                <br />
                <Button onClick={handleSubmit} colorScheme='blue' w={["60%", "70%", "30%", "20%"]} m="auto">Submit</Button>
            </Center>

        </Box>
    )
}

export default SelectLocation