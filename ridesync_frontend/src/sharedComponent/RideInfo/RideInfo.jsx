import { Box, Flex, useToast, Table, TableContainer, Thead, Tr, Th, Tbody, Td, Tfoot, TableCaption, Button, Tooltip, useBreakpointValue, Badge } from "@chakra-ui/react";
import { useEffect, useState } from "react";
import { API } from "../../sharedComponent/API";
import axios from "axios";
import { useLocation, useNavigate } from "react-router-dom";
import Navbar from "../../Components/Navbar/Navbar";
import BottomNavbar from "../../Components/Navbar/BottomNavbar";
import { FiCheck, FiX, FiMoreVertical } from 'react-icons/fi';

const Activity = ({ route }) => {
    const navigate = useNavigate();
    const location = useLocation();
    const { rideId, is_driver } = location.state;
    const [isLoading, setIsLoading] = useState(null);
    const [loggedInUserDetails, setLoggedInUserDetails] = useState({});
    const isMobile = useBreakpointValue({ base: true, md: false });
    const [requests, setRequests] = useState([]);
    const toast = useToast();

    const handleRequestUpdate = (requestId, status) => {
        const reqBody = {
            "requestStatus": status
        }
        const config = {
            headers: { Authorization: `Bearer ${loggedInUserDetails.token}` }
        };
        axios.put(`${API}/request/updateRideRequest/${requestId}`, reqBody, config).then(resp => {
            if (resp.data.success) {
                setRequests(currentRequests => currentRequests.map(request =>
                    request.rideRequestId === requestId ? { ...request, requestStatus: status } : request
                ));
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

    useEffect(() => {
        const loggedInUserInfo = JSON.parse(localStorage.getItem('loggedInUserDetails'));
        if (loggedInUserInfo) {
            setIsLoading(true);
            setLoggedInUserDetails(loggedInUserInfo);
            console.log({ rideId, is_driver })
            const config = {
                headers: { Authorization: `Bearer ${loggedInUserInfo.token}` }
            };
            axios.get(`${API}/ride/getRideDetail/${rideId}`, config)
                .then((resp) => {
                    if (resp.data.success) {
                        if (is_driver)
                            axios.get(`${API}/request/getRideRequest?rideId=${rideId}`, config)
                                .then((response) => {
                                    if (response.data.success)
                                        console.log(response.data.requests);
                                    setRequests(response.data.requests)
                                })
                    }
                })
                .catch((err) => {
                    console.log("err in retriving rider activity", err.message);
                }).finally(() => setIsLoading(false));
        }
    }, [])

    return (
        <>
            {isMobile ? <BottomNavbar /> : <Navbar />}
            <br />
            <Box p="10px" zIndex="-10">
                <Flex w={["100%", "95%", "75%", "75%"]} h={["85vh", "85vh", "80vh", "80vh"]} m="auto" justifyContent='flex-start' align="top" flexDir={["column", "column", "row", "row"]}>
                    {
                        requests && requests.length > 0 &&
                        <TableContainer>
                            <Table variant='simple' size={isMobile ? "sm" : "md"}>
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
                                        const tripStartTime = request.tripStartTime[0] + "/" + request.tripStartTime[1] + "/" + request.tripStartTime[2] + " " + request.tripStartTime[3] + ":" + request.tripStartTime[4];
                                        return (
                                            <Tr>
                                                <Td>
                                                    {request.requestStatus !== "REQUESTED" &&
                                                        <Badge colorScheme={request.requestStatus === "ACCEPTED" ? 'green' : "red"}>{request.requestStatus}</Badge>
                                                    }
                                                    {request.requestStatus === "REQUESTED" && actions.map((action, index) => (
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
                                                <Td>{request.riderId}</Td>
                                                <Td>Barrigton Street</Td>
                                                <Td>Duke Street</Td>
                                                <Td>
                                                    {tripStartTime}
                                                </Td>
                                            </Tr>
                                        )
                                    })}

                                </Tbody>
                            </Table>
                        </TableContainer>
                    }

                </Flex>
            </Box >
        </>
    )
}

export default Activity