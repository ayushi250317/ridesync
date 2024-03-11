import { Box, CardBody, CardHeader, Flex, Heading, Stack, Tab, TabIndicator, TabList, TabPanel, TabPanels, Tabs, useMediaQuery, Text, Card, StackDivider, Divider, Button } from "@chakra-ui/react";
import BottomNavbar from "../Navbar/BottomNavbar";
import Navbar from "../Navbar/Navbar";
import { useEffect, useState } from "react";
import { API } from "../../sharedComponent/API";
import axios from "axios";
import { useNavigate } from "react-router-dom";

const Activity = () => {
    const navigate = useNavigate();
    const [isLoading, setIsLoading] = useState(null);
    const [loggedInUserDetails, setLoggedInUserDetails] = useState({});
    const [rides, setRides] = useState({
        "posted": [],
        "active": [],
        "completed": []
    });
    const [isLargerThan1280] = useMediaQuery('(min-width: 700px)');

    useEffect(() => {
        const loggedInUserInfo = JSON.parse(localStorage.getItem('loggedInUserDetails'));
        if (loggedInUserInfo) {
            setLoggedInUserDetails(loggedInUserInfo);
            const config = {
                headers: { Authorization: `Bearer ${loggedInUserInfo.token}` }
            };
            axios.get(`${API}/ride/get/${loggedInUserInfo.user.userId}`, config)
                .then((resp) => {
                    if (resp.data.success) {
                        const segregatedRides = {
                            posted: [],
                            active: [],
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

    const TabBody = ({ rideType }) => {
        const buttonAttributes = {
            variant: "unstyled",
            color: "gray",
            _focus: { boxShadow: 'none' },
            _hover: { color: 'gray.600' },
            _active: { color: 'gray.800' },
        };
        return (
            <Flex w="100%" direction="column">
                {rides[rideType].map((ride, index) => {
                    return (
                        <Card w="100%" id={index}>
                            <CardHeader>
                                <Heading size={isLargerThan1280 ? 'md' : 'sm'}
                                    textTransform='uppercase'
                                    _hover={{ cursor: "pointer" }}
                                    onClick={() => navigate("/ride_info", { state: { rideId: ride.rideId } })}
                                >
                                    {ride.startLocationAddress.split(',')[0] + ' to ' + ride.endLocationAddress.split(',')[0]}
                                </Heading>
                                <Text> {`${ride.originalTripStartTime[0]}/${ride.originalTripStartTime[1]}/${ride.originalTripStartTime[2]} at  ${ride.originalTripStartTime[3]}:${ride.originalTripStartTime[4]}`}</Text>
                            </CardHeader>

                            <CardBody>
                                {ride.isDriver && <>
                                    {rideType === "posted" && <>
                                        <Box>
                                            <Button {...buttonAttributes}>
                                                Cancel ride
                                            </Button>
                                        </Box>
                                        <Divider orientation='horizontal' padding="5px" />
                                        <Box>
                                            <Button {...buttonAttributes}>
                                                Edit ride
                                            </Button>
                                        </Box>
                                    </>}
                                    {rideType === "current" && <>
                                        <Box>
                                            <Button {...buttonAttributes}>
                                                Track
                                            </Button>
                                        </Box>
                                    </>}
                                </>
                                }
                                {!ride.isDriver && rideType === "posted" &&
                                    <>
                                        {rideType === "posted" && <>
                                            <Box>
                                                <Button {...buttonAttributes}>
                                                    Cancel ride
                                                </Button>
                                            </Box>
                                        </>}
                                        {rideType === "current" && <>
                                            <Button {...buttonAttributes}>
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
                                    <TabBody rideType="active" />
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