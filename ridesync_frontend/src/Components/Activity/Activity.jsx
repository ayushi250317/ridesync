import { Box, CardBody, CardHeader, Flex, Heading, Tab, TabIndicator, TabList, TabPanel, TabPanels, Tabs, useMediaQuery, Text, Card, Divider, Button, Center, Spinner, useToast } from "@chakra-ui/react";
import BottomNavbar from "../Navbar/BottomNavbar";
import Navbar from "../Navbar/Navbar";
import { useEffect, useState } from "react";
import { API } from "../../sharedComponent/API";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import { GiSteeringWheel } from "react-icons/gi";

const Activity = ({ }) => {
    const navigate = useNavigate();
    const [isLoading, setIsLoading] = useState(null);
    const [loggedInUserDetails, setLoggedInUserDetails] = useState({});
    const [rides, setRides] = useState({
        "posted": [],
        "ongoing": [],
        "completed": []
    });
    const toast = useToast();
    const [isLargerThan1280] = useMediaQuery('(min-width: 700px)');

    useEffect(() => {
        const loggedInUserInfo = JSON.parse(localStorage.getItem('loggedInUserDetails'));
        if (loggedInUserInfo) {
            setIsLoading(true);
            setLoggedInUserDetails(loggedInUserInfo);
            const config = {
                headers: { Authorization: `Bearer ${loggedInUserInfo.token}` }
            };
            axios.get(`${API}/ride/getRides/${loggedInUserInfo.user.userId}`, config)
                .then((resp) => {
                    if (resp.data.success) {
                        const segregatedRides = {
                            posted: [],
                            ongoing: [],
                            completed: []
                        };
                        console.log(resp.data)
                        resp.data.responseObject.forEach(ride => {
                            if (segregatedRides[ride.status] !== undefined) {
                                segregatedRides[ride.status].push(ride);
                            }
                        });
                        setRides(segregatedRides);
                    }
                })
                .catch((err) => {
                    console.log("err in retriving rider activity", err.message);
                }).finally(() => setIsLoading(false));
        }
    }, [])
    useEffect(() => {
        console.log('Rides updated', rides);
    }, [rides]);

    const TabBody = ({ rideType }) => {
        const buttonAttributes = {
            variant: "unstyled",
            color: "gray",
            _focus: { boxShadow: 'none' },
            _hover: { color: 'gray.600' },
            _active: { color: 'gray.800' },
        };
        console.log("rrrr", rides)
        return (
            <Flex w="100%" direction="column">
                {rides[rideType].map((ride, index) => {
                    return (
                        <Card w="100%" id={index} mt="10px" variant='filled'>
                            <CardHeader>
                                <Flex alignItems="center" justifyContent="space-between">
                                    <Heading size={isLargerThan1280 ? 'md' : 'sm'}
                                        textTransform='uppercase'
                                        _hover={{ cursor: "pointer" }}
                                        onClick={() => navigate("/ride_info", { state: { rideId: ride.rideId, isDriver: ride.isDriver } })}
                                    >
                                        {ride.startLocationAddress.split(',')[0] + ' to ' + ride.endLocationAddress.split(',')[0]}
                                    </Heading>
                                    {ride.isDriver && <GiSteeringWheel size={30} />}
                                </Flex>
                                <Text> {`${ride.originalTripStartTime[0]}/${ride.originalTripStartTime[1]}/${ride.originalTripStartTime[2]} at  ${ride.originalTripStartTime[3]}:${ride.originalTripStartTime[4]}`}</Text>
                            </CardHeader>

                            <CardBody>
                                {ride.isDriver && <>
                                    {rideType === "posted" && <>
                                        <Box>
                                            <Button {...buttonAttributes} onClick={() => { changeRideStatus(ride.rideId, 'ongoing', 'posted') }}>
                                                Start ride
                                            </Button>
                                        </Box>
                                    </>}
                                    {rideType === "ongoing" && <>
                                        <Box>
                                            <Button {...buttonAttributes} onClick={() => { trackRide(ride.rideId, ride.isDriver) }}>
                                                Track Riders
                                            </Button>
                                        </Box>
                                        <Divider orientation='horizontal' padding="5px" colorScheme="gray.800" />
                                        <Box>
                                            <Button {...buttonAttributes} onClick={() => { changeRideStatus(ride.rideId, 'completed', 'ongoing') }}>
                                                End ride
                                            </Button>
                                        </Box>
                                    </>}
                                </>
                                }
                                {!ride.isDriver &&
                                    <>
                                        {rideType === "posted" && <>
                                            <Box>
                                                <Button {...buttonAttributes} onClick={() => { trackRide(ride.rideId) }}>
                                                    Cancel ride
                                                </Button>
                                            </Box>
                                        </>}
                                        {rideType === "ongoing" && <>
                                            <Button {...buttonAttributes} onClick={() => navigate("/livelocationtracking", { state: { rideId: ride.rideId, isDriver: ride.isDriver } })}>
                                                Track Driver
                                            </Button>
                                        </>}
                                    </>
                                }
                            </CardBody>
                        </Card>
                    )
                })}
                <br />
            </Flex>
        )
    }

    const changeRideStatus = (rideId, newStatus, currentStatus) => {
        const reqBody = {
            "rideId": rideId,
            "rideStatus": newStatus
        }
        const config = {
            headers: { Authorization: `Bearer ${loggedInUserDetails.token}` }
        };
        axios.put(`${API}/ride/updateRideStatus`, reqBody, config).then(resp => {
            if (resp.data.success) {
                setRides(prevRides => {
                    const newCurrentStatusRides = [...prevRides[currentStatus]];
                    const newNewStatusRides = [...prevRides[newStatus]];

                    const rideToUpdateIndex = newCurrentStatusRides.findIndex(ride => ride.rideId === rideId);

                    if (rideToUpdateIndex > -1) {
                        const rideToUpdate = { ...newCurrentStatusRides[rideToUpdateIndex], status: newStatus };
                        newCurrentStatusRides.splice(rideToUpdateIndex, 1);
                        newNewStatusRides.push(rideToUpdate);
                        return {
                            ...prevRides,
                            [currentStatus]: newCurrentStatusRides,
                            [newStatus]: newNewStatusRides,
                        };
                    }

                    return prevRides; // If no update is needed, return the previous state
                });
                toast({
                    title: newStatus === "ongoing" ? "ride has started" : "ride has ended",
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
    }

    const trackRide = (rideId, isDriver) => {
        navigate("/livelocationtracking", { state: { rideId, isDriver } });
    }

    if (isLoading) return <Center h="80vh">
        <Spinner size='xl' />
    </Center>

    return (
        <>
            {isLargerThan1280 ? <Navbar /> : <BottomNavbar />}
            <br />
            <Box p="10px" zIndex="-10">
                <Flex w={["100%", "95%", "75%", "75%"]} h={["85vh", "85vh", "80vh", "80vh"]} m="auto" justifyContent='flex-start' align="top" flexDir={["column", "column", "row", "row"]}>
                    <Flex w="100%" justifyContent={isLargerThan1280 ? 'flex-start' : 'center'}>
                        <Tabs
                            w="100%"
                            isFitted
                            variant={isLargerThan1280 ? 'unstyled' : 'solid-rounded'}
                            size={isLargerThan1280 ? 'md' : 's'}
                            colorScheme='gray'>
                            <TabList w={["100%", "100%", "70%", "55%"]}>
                                <Tab>Current Rides</Tab>
                                <Tab>Upcoming Rides</Tab>
                                <Tab>Past Rides</Tab>
                            </TabList>
                            {isLargerThan1280 &&
                                <TabIndicator
                                    mt="-1.5px"
                                    height="2px"
                                    bg="gray.500"
                                    borderRadius="1px"
                                />
                            }
                            <TabPanels w="100%">
                                <TabPanel>
                                    <TabBody rideType="ongoing" />
                                </TabPanel>
                                <TabPanel>
                                    <TabBody rideType="posted" />
                                </TabPanel>
                                <TabPanel>
                                    <TabBody rideType="completed" />
                                </TabPanel>
                            </TabPanels>
                        </Tabs>
                    </Flex>
                </Flex>
            </Box>
        </>
    )
}

export default Activity