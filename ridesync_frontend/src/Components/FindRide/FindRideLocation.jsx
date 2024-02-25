import { Box, Button, Center, Flex, Input, Text } from '@chakra-ui/react'
import React, { useEffect, useState } from 'react'
import Locations from '../../sharedComponent/Maps/Locations'
import { MdAccessTimeFilled } from "react-icons/md";
import axios from 'axios';
import { API } from '../../sharedComponent/API';
const FindRideLocation = () => {
    const [loggedInUserDetails, setLoggedInUserDetails] = useState({});
    const [findRidesResult, setFindRidesResult] = useState([])
    const [loading, setLoading] = useState(false)
    useEffect(() => {
        const loggedInUserInfo = JSON.parse(
            localStorage.getItem("loggedInUserDetails")
        );
        if (loggedInUserInfo) {
            setLoggedInUserDetails(loggedInUserInfo);
        }
    }, []);
    const [fromAddress, setFromAddress] = useState({
        address: "",
        lat: null,
        lng: null
    });
    const [toAddress, setToAddress] = useState({
        address: "",
        lat: null,
        lng: null
    });
    const [rideTime, setRideTime] = useState("")
    console.log({ fromAddress, toAddress, rideTime });

    const handleSubmitFindRide = () => {
        const newObj = {
            source: {
                lat: fromAddress.lat,
                lng: fromAddress.lng,
            },
            destination: {
                lat: toAddress.lat,
                lng: toAddress.lng
            },
            rideTime: `${rideTime}:00.000000`
        }
        console.log("new obj", newObj);
        setLoading(true)
        const config = {
            headers: { Authorization: `Bearer ${loggedInUserDetails.token}` },
        };
        axios.post(`${API}/geo/search`, newObj, config).then(resp => {
            console.log("search ride", resp.data);
            if (resp.data.success) {
                setFindRidesResult(resp.data.responseObject)
            }
        }).catch(err => {
            console.log("err in find ride", err);
        }).finally(() => {
            setLoading(false)
        })
    }

    return (
        <Box w={["88%", "92%", "63%", "67%"]} m="auto">

            <Center flexDir="column" mt="5" >
                <Text textAlign="center" fontSize="4xl" mb="3">Find Rides</Text>

                <Locations fromAddress={fromAddress} toAddress={toAddress} setFromAddress={setFromAddress} setToAddress={setToAddress} />

                <Flex w="100%" justifyContent="center" alignItems="center" ml="2">

                    <Box mr="2">
                        <MdAccessTimeFilled size="24px" />
                    </Box>
                    <Input type='datetime-local' placeholder='Enter start time' w="98%" value={rideTime} onChange={(e) => setRideTime(e.target.value)}></Input>
                </Flex>
                <Button onClick={handleSubmitFindRide} mt="6" w={["70%", "70%", "50%", "30%"]} colorScheme='blue' isLoading={loading}>Submit</Button>
            </Center>

            <Box>
                {findRidesResult.length ? <Text fontSize="3xl" textAlign="center" mt="6">Available Rides</Text> : <Text fontSize="xl" textAlign="center" mt="6"> ☹️ No Rides Available</Text>}
                {findRidesResult.map(rides => {
                    return <Box border="1px solid lightgray" borderRadius="xl" m="10" p="5" boxShadow="xl">
                        <Flex justifyContent="space-between">
                            <Box>

                                <Text>

                                    Start Location: {rides.startLocationAddress}
                                </Text>
                                <Text>
                                    End Location: {rides.endLocationAddress}
                                </Text>
                                <Text>Start Date: {`${rides.startTime[0]}/${rides.startTime[1]}/${rides.startTime[2]} ${rides.startTime[3]}:${rides.startTime[4]}`}</Text>
                            </Box>

                        </Flex>

                        <Center justifyContent="space-between">

                            <Text>
                                Seat available : {rides.seatsAvailable}
                            </Text>

                            <Button colorScheme='green'>Request Ride</Button>
                        </Center>
                    </Box>
                })}
            </Box>

        </Box >
    )
}

export default FindRideLocation