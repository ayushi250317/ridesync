import { Box, Button, Center, Flex, Input, Text, useToast } from '@chakra-ui/react'
import React, { useEffect, useState } from 'react'
import Locations from '../../sharedComponent/Maps/Locations'
import { MdAccessTimeFilled } from "react-icons/md";
import axios from 'axios';
import { API } from '../../sharedComponent/API';
import { useNavigate } from 'react-router-dom';
const FindRideLocation = () => {
    const navigate = useNavigate();
    const toast = useToast();
    const [loggedInUserDetails, setLoggedInUserDetails] = useState({});
    const [findRidesResult, setFindRidesResult] = useState([])
    const [loading, setLoading] = useState(false)
    const [reqSent, setReqSent] = useState(false)

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

    useEffect(() => {
        const loggedInUserInfo = JSON.parse(
            localStorage.getItem("loggedInUserDetails")
        );
        if (loggedInUserInfo) {
            setLoggedInUserDetails(loggedInUserInfo);
            const searchInfo = JSON.parse(
                localStorage.getItem("searchInfo")
            );
            if (searchInfo && searchInfo !== null) {
                const hasAllKeys = ["fromAddress", "toAddress", "rideTime"].every(key => Object.keys(searchInfo).includes(key));
                if (hasAllKeys) {
                    setFromAddress(searchInfo.fromAddress);
                    setToAddress(searchInfo.toAddress);
                    setRideTime(searchInfo.rideTime);
                    handleSubmitFindRide(searchInfo.fromAddress, searchInfo.toAddress, searchInfo.rideTime, loggedInUserInfo.token);
                    localStorage.removeItem('searchInfo');
                }
            }
        }
    }, []);
    const handleSubmitFindRide = (fromAdd, toAdd, time, token) => {
        const newObj = {
            source: {
                lat: fromAdd.lat,
                lng: fromAdd.lng,
            },
            destination: {
                lat: toAdd.lat,
                lng: toAdd.lng
            },
            rideTime: `${time}:00.000000`
        }
        console.log("new obj", newObj);
        setLoading(true)
        setReqSent(true)
        const config = {
            headers: { Authorization: `Bearer ${token}` },
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

    const handleRequestRide = (rideId, driverId) => {
        const newReqObj = {
            estimatedTripStartTime: rideTime,
            lattitude1: fromAddress.lat,
            longitude1: fromAddress.lng,
            landmark1: "",
            address1: fromAddress.address,
            lattitude2: toAddress.lat,
            longitude2: toAddress.lng,
            landmark2: "",
            address2: toAddress.address,
            rideId,
            driverId
        }
        console.log({ newReqObj })
        const config = {
            headers: { Authorization: `Bearer ${loggedInUserDetails.token}` },
        };
        axios.post(`${API}/request/addRequest`, newReqObj, config).then(resp => {
            console.log("request ride", resp.data);
            if (resp.data.success) {
                toast({
                    title: "Ride requested successfully.",
                    status: 'success',
                    duration: 5000,
                    isClosable: true,
                });
            }
        }).catch(err => {
            console.log("err in req ride", err);
        }).finally(() => {
        })

    }

    return <Box w={["88%", "92%", "63%", "67%"]} m="auto">

        <Center flexDir="column" mt="5" >
            <Text textAlign="center" fontSize="4xl" mb="3">Find Rides</Text>

            <Locations fromAddress={fromAddress} toAddress={toAddress} setFromAddress={setFromAddress} setToAddress={setToAddress} />

            <Flex w="100%" justifyContent="center" alignItems="center" ml="2">

                <Box mr="2">
                    <MdAccessTimeFilled size="24px" />
                </Box>
                <Input type='datetime-local' placeholder='Enter start time' w="98%" value={rideTime} onChange={(e) => setRideTime(e.target.value)} />
            </Flex>
            <Button onClick={() => { handleSubmitFindRide(fromAddress, toAddress, rideTime, loggedInUserDetails.token) }} mt="6" w={["70%", "70%", "50%", "30%"]} colorScheme='blue' isLoading={loading}>Submit</Button>
        </Center>

        <Box>
            {reqSent && findRidesResult.length === 0 ? <Text fontSize="xl" textAlign="center" mt="6"> ☹️ No Rides Available</Text> :
                <Box>
                    {reqSent && <Text fontSize="3xl" textAlign="center" mt="6">Available Rides</Text>}
                    {findRidesResult.map(rides => {
                        return <Box border="1px solid lightgray" borderRadius="xl" m={["4", "4", "8", "10"]} p="5" boxShadow="xl">
                            <Flex justifyContent="space-between">
                                <Box>
                                    <Text>
                                        Start Location: {rides.startLocationAddress}
                                    </Text>
                                    <Text>
                                        End Location: {rides.endLocationAddress}
                                    </Text>
                                    <Text>Start Date: {`${rides.startTime[0]}/${rides.startTime[1]}/${rides.startTime[2]} ${rides.startTime[3]}:${rides.startTime[4]}`}</Text>
                                    <Text>
                                        Seat available : {rides.seatsAvailable}
                                    </Text>
                                    <Text>
                                        Fare: ${rides.fare}
                                    </Text>
                                </Box>

                            </Flex>
                            <Flex justifyContent="flex-end" mt="1">
                                <Button colorScheme='green' mr="1" isDisabled={!rides.enableRequestRide} onClick={() => handleRequestRide(rides.rideId, rides.driverId)}>{!rides.enableRequestRide ? "Already Requested" : "Request Ride"}</Button>
                                <Button colorScheme='blue'
                                    onClick={() => {
                                        localStorage.setItem('searchInfo', JSON.stringify({ fromAddress, toAddress, rideTime }));
                                        navigate("/ride_info", { state: { rideId: rides.rideId, isDriver: false } })
                                    }}>View Ride
                                </Button>
                            </Flex>
                        </Box>
                    })}
                </Box>
            }
        </Box >
    </Box>


}

export default FindRideLocation;