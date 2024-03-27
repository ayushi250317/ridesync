/* eslint-disable react-hooks/exhaustive-deps */
/* eslint-disable no-unused-vars */
import { Center, Flex, useToast, Table, TableContainer, Thead, Tr, Th, Tbody, Td, Tfoot, TableCaption, Button, Tooltip, useBreakpointValue, Badge, Heading, Text, Card, CardHeader, CardBody, CardFooter, SimpleGrid, Stack, Spinner, Box } from "@chakra-ui/react";
import { useEffect, useState } from "react";
import { API, GMAP_API_KEY } from "../../sharedComponent/API";
import axios from "axios";
import { useLocation, useNavigate } from "react-router-dom";
import Navbar from "../../Components/Navbar/Navbar";
import BottomNavbar from "../../Components/Navbar/BottomNavbar";
import { FiCheck, FiX, FiMoreVertical } from 'react-icons/fi';
import { GiSteeringWheel } from "react-icons/gi";
import { SiLivechat } from "react-icons/si";
import { GrMapLocation } from "react-icons/gr";
import { GoogleMap, useJsApiLoader, Marker, DirectionsRenderer } from '@react-google-maps/api';
import { dateAndTimeInString, extractAddress } from "../Utils";
import ChatDrawer from "../Chat/ChatDrawer";

const libraries = ['places'];

const Activity = ({ route }) => {
    const { isLoaded } = useJsApiLoader({
        googleMapsApiKey: GMAP_API_KEY,
        libraries,
    });
    const backbtn = "<"
    // const navigate = useNavigate();
    const toast = useToast();
    const isMobile = useBreakpointValue({ base: true, md: false });
    const location = useLocation();
    const { rideId, isDriver } = location.state;
    const [isLoading, setIsLoading] = useState(null);
    const [loggedInUserDetails, setLoggedInUserDetails] = useState({});
    const [requests, setRequests] = useState(null);
    const [riders, setRiders] = useState([])
    const [rideInfo, setRideInfo] = useState({});
    const [directionsResponse, setDirectionsResponse] = useState(null);
    const [markers, setMarkers] = useState([]);
    const [isChatDrawerOpen, setIsChatDrawerOpen] = useState(false);
    const [chatUserId, setchatUserId] = useState(null);
    useEffect(() => {
        const loggedInUserInfo = JSON.parse(localStorage.getItem('loggedInUserDetails'));
        if (loggedInUserInfo) {
            setIsLoading(true);
            setLoggedInUserDetails(loggedInUserInfo);
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
            }
            axios.get(`${API}/ride/getRideDetail/${rideId}`, config)
                .then((resp) => {
                    if (resp.data.success) {
                        let rideInfoResponse = resp.data.responseObject.rideProjection[0];
                        setRiders(resp.data.responseObject.rideInfoProjection);
                        setRideInfo(rideInfoResponse);
                        calculateRoute(rideInfoResponse.startLocationAddress, rideInfoResponse.endLocationAddress)
                        if (isDriver)
                            axios.get(`${API}/request/getRideRequest?rideId=${rideId}`, config)
                                .then((response) => {
                                    if (response.data.success)
                                        setRequests(response.data.responseObject)
                                })

                    }
                })
                .catch((err) => {
                    console.log("err in retriving rider activity", err.message);
                }).finally(() => setIsLoading(false));
        }
    }, [])

    const toggleChatDrawer = () => setIsChatDrawerOpen(!isChatDrawerOpen);

    const handleRequestUpdate = (requestId, status) => {
        if (status === "ACCEPTED" && rideInfo.seatsAvailable <= riders.length + 1) {
            toast({
                title: "Max capacity reached",
                status: 'error',
                duration: 5000,
                isClosable: true,
            });
            return
        }
        const reqBody = {
            "requestStatus": status
        }
        const config = {
            headers: { Authorization: `Bearer ${loggedInUserDetails.token}` }
        };
        axios.put(`${API}/request/updateRideRequest/${requestId}`, reqBody, config).then(resp => {
            if (resp.data.success) {
                let modifiedRequest;
                setRequests(currentRequests => currentRequests.map(request => {
                    if (request.rideRequestId === requestId) {
                        modifiedRequest = request
                        return { ...request, requestStatus: status }
                    } else {
                        return request
                    }
                }
                ));
                if (status === "ACCEPTED") {
                    let newRider = {
                        "riderName": modifiedRequest.riderName,
                        "riderId": modifiedRequest.riderId,
                        "IsDriver": false,
                        "startLocationAddress": modifiedRequest.pickupAddress,
                        "startLocationLandmark": "",
                        "startLat": modifiedRequest.pickupLat,
                        "startLong": modifiedRequest.pickupLong,
                        "endLocationAddress": modifiedRequest.dropAddress,
                        "endLocationLandmark": "",
                        "endLat": modifiedRequest.dropLat,
                        "endLong": modifiedRequest.dropLong,
                        "fare": 0,
                        "comments": null,
                        "rating": null,
                        "waitTime": null,
                        "riderTripStartTime": modifiedRequest.tripStartTime,
                        "riderTripEndTime": null
                    }
                    setRiders(currentRiders => [...currentRiders, newRider])
                }
                toast({
                    title: "Request updated",
                    status: "success",
                    duration: 5000,
                    isClosable: true,
                });
            }
        }).catch(err => {
            toast({
                title: "operation failed",
                status: 'error',
                duration: 5000,
                isClosable: true,
            });
            console.log("err in find ride", err);
        }).finally(() => {
        })
    };

    const handleActionClick = (request, label) => {
        if (label === "Accept" || label === "Reject") {
            let status = label === "Accept" ? "ACCEPTED" : "REJECTED";
            handleRequestUpdate(request.rideRequestId, status)
        }
    }

    const actions = [
        { icon: FiCheck, label: 'Accept', tooltipLabel: 'Accept', colorScheme: 'blue', onClick: handleActionClick },
        { icon: FiX, label: 'Reject', tooltipLabel: 'Reject', colorScheme: 'red', onClick: handleActionClick },
        { icon: FiMoreVertical, label: 'More', tooltipLabel: 'More actions', colorScheme: 'gray', onClick: handleActionClick },
    ];

    const RiderFooter = ({ isDriverCard, isDriver, riderInfo, index }) => {
        let newMarkers = [{ lat: riderInfo.startLat, lng: riderInfo.startLong, label: { text: 'S', color: 'white', background: 'green' } },
        { lat: riderInfo.endLat, lng: riderInfo.endLong, label: { text: 'D', color: 'white', background: 'red' } },]
        if (isDriverCard && isDriver) {
            return <></>
        } else if (isDriverCard && !isDriver) {
            return (<Flex>
                <Button leftIcon={<SiLivechat />} variant='ghost' onClick={() => {
                    toggleChatDrawer();
                    setchatUserId(riderInfo.riderId);
                }}>
                    {isMobile ? '' : 'chat'}
                </Button>
            </Flex>)
        } else if (!isDriverCard && isDriver) {
            return (
                <Stack direction='row' spacing={1}>
                    <Button leftIcon={<SiLivechat />} variant='ghost' onClick={() => {
                        toggleChatDrawer();
                        setchatUserId(riderInfo.riderId);
                    }}>
                        {isMobile ? '' : 'chat'}
                    </Button>
                    <Button leftIcon={<GrMapLocation />} variant='ghost' onClick={() => {
                        setMarkers([...markers, ...newMarkers]);
                    }}>
                        {isMobile ? '' : 'map'}
                    </Button>
                </Stack>)
        }
    }

    if (isLoading) return <Center h="80vh">
        <Spinner size='xl' />
    </Center>
    else {
        return (
            <>
                {isMobile ? <BottomNavbar /> : <Navbar />}
                <br />
                <Flex w={["100%", "95%", "75%", "75%"]} h={["90vh", "90vh", "85vh", "85vh"]} m="auto" justifyContent='flex-start' align="top" flexDir="column" pb={10} pr={10} pl={10} overflowY={isMobile ? 'auto' : ''}>
                    {isMobile &&
                        <Button position="absolute" top="5" right="345" backgroundColor="transparent" fontSize="2xl"
                            onClick={() => window.history.go(-1)}>   {backbtn} </Button>
                    }
                    <Flex direction="column" align="flex-start" w="100%">
                        <Text>
                            <Heading size={"xl"} >{extractAddress(rideInfo.startLocationAddress) + " to " + extractAddress(rideInfo.endLocationAddress)}</Heading>
                        </Text>
                        <Text fontSize="lg" fontWeight={600}>Leaving {dateAndTimeInString(rideInfo.originalTripStartTime)}</Text>
                        {/* <Flex mt={4}><Text fontWeight={500} fontSize="lg" mr={1}>Start Point: </Text><Text pt={0.5}>{rideInfo.startLocationAddress}</Text></Flex>
                        <Flex mt={2}><Text fontWeight={500} fontSize="lg" mr={1}>End Point: </Text><Text pt={0.5}>{rideInfo.endLocationAddress}</Text></Flex> */}
                        <Text fontSize="sm" mt={3} mb={5} fontStyle="italic">{rideInfo.description}</Text>
                    </Flex>
                    {
                        isDriver && requests &&
                        <Flex flexDir="column">
                            <Text fontWeight={500} fontSize="lg" mr={1} mb={1}>Request: </Text>

                            {requests.length > 0 ? <TableContainer mb={5}>
                                <Table variant='simple' size={isMobile ? "sm" : "md"} >
                                    <Thead>
                                        <Tr>
                                            <Th>Actions / status</Th>
                                            <Th>Rider Name</Th>
                                            <Th>Pickup Point</Th>
                                            <Th>Drop Point</Th>
                                            <Th>Pickup Time</Th>
                                        </Tr>
                                    </Thead>
                                    <Tbody>
                                        {requests.map((request) => {
                                            const tripStartTime = dateAndTimeInString(request.tripStartTime);
                                            return (
                                                <Tr>
                                                    <Td>
                                                        {(request.requestStatus !== "REQUESTED" || rideInfo.status !== "posted") &&
                                                            <Badge colorScheme={request.requestStatus === "ACCEPTED" ? 'green' : "red"}>{request.requestStatus}</Badge>
                                                        }
                                                        {(request.requestStatus === "REQUESTED" && rideInfo.status === "posted") && actions.map((action, index) => (
                                                            <Tooltip key={index} label={isMobile ? action.tooltipLabel : ''} hasArrow>
                                                                <Button
                                                                    colorScheme={action.colorScheme}
                                                                    size="sm"
                                                                    variant='ghost'
                                                                    leftIcon={<action.icon />}
                                                                    onClick={() => { action.onClick(request, action.label) }}
                                                                >
                                                                    {!isMobile && action.label}
                                                                </Button>
                                                            </Tooltip>
                                                        ))}</Td>
                                                    <Td>{request.riderName}</Td>
                                                    <Td>{request.pickupAddress.split(',')[0]}</Td>
                                                    <Td>{request.dropAddress.split(',')[0]}</Td>
                                                    <Td>
                                                        {tripStartTime}
                                                    </Td>
                                                </Tr>
                                            )
                                        })}

                                    </Tbody>
                                </Table>
                            </TableContainer> : <Text fontSize="xl" textAlign="center" mb="5"> ☹️ No Request Received</Text>}

                        </Flex>
                    }
                    <Flex flexDir="column" width="100%">

                        <Text fontWeight={500} fontSize="lg" mr={1} mb={1}>Seat Plan: </Text>
                        <SimpleGrid spacing={4} templateColumns='repeat(auto-fill, minmax(200px, 1fr))' mb={5} width={"100%"}>
                            {
                                riders && Array.from({ length: rideInfo.seatsAvailable + 1 }, (_, index) => {
                                    if (riders[index]) {
                                        let { riderName, IsDriver, startLocationAddress, endLocationAddress, riderTripStartTime } = riders[index];
                                        return (
                                            <Card>
                                                <CardHeader>
                                                    <Flex alignItems="center" justifyContent="space-between">
                                                        <Heading size={isMobile ? 'md' : 'sm'}
                                                            textTransform='uppercase'
                                                        >
                                                            {riderName}
                                                        </Heading>
                                                        {IsDriver && <GiSteeringWheel size={30} />}
                                                    </Flex>
                                                </CardHeader>
                                                <CardBody>
                                                    {isDriver && !IsDriver && <Flex direction="column">
                                                        <Text><b>Pickup Location:</b>{" " + startLocationAddress}</Text>
                                                        <Text><b>Drop Location:</b>{" " + endLocationAddress}</Text>
                                                        <Text><b>Pickup Time:</b>{" " + riderTripStartTime[3] + ":" + riderTripStartTime[4]}</Text>
                                                    </Flex>}
                                                </CardBody>
                                                <CardFooter>
                                                    <RiderFooter isDriverCard={IsDriver} isDriver={isDriver} riderInfo={riders[index]} index={index} />
                                                </CardFooter>
                                            </Card>
                                        )
                                    }
                                    else {
                                        return (
                                            <Card>
                                                <CardHeader>
                                                    <Heading size={isMobile ? 'md' : 'sm'}>
                                                        Seat Available
                                                    </Heading>
                                                </CardHeader>
                                            </Card>
                                        )
                                    }
                                }
                                )
                            }

                        </SimpleGrid>
                    </Flex>
                    <Flex h="100%" w="100%" flexDir="column">
                        <Text fontWeight={500} fontSize="lg" mr={1} mb={1}>Map: </Text>
                        <Box h='400px' w='100%' mb={5}>

                            <GoogleMap
                                zoom={13}
                                mapContainerStyle={{ width: '100%', height: '100%' }}
                                options={{
                                    zoomControl: false,
                                    streetViewControl: false,
                                    mapTypeControl: false,
                                    fullscreenControl: false,
                                }}
                            >
                                {directionsResponse && <DirectionsRenderer directions={directionsResponse} />}
                                {markers.map(marker => (
                                    <Marker
                                        key={marker.id}
                                        position={{ lat: marker.lat, lng: marker.lng }}
                                        label={marker.label}
                                        title="Test"
                                    />
                                ))}
                                {markers.length > 0 &&

                                    <Button
                                        onClick={() => { setMarkers([]); }}
                                        variant="ghost"
                                        size="sm"
                                        position="absolute"
                                        top="10px"
                                        right="10px"
                                        zIndex="10"
                                        className='mapBackground'
                                    >
                                        Clear
                                    </Button>
                                }
                            </GoogleMap>
                        </Box>
                    </Flex>
                </Flex>
                <ChatDrawer
                    isOpen={isChatDrawerOpen}
                    onClose={toggleChatDrawer}
                    chatPartnerId={chatUserId}
                />
            </>
        )
    }
}

export default Activity