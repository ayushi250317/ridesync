import { Box, Flex, useMediaQuery, Text, Button, Tr, Th, Tbody, Table, Td, Tfoot, Thead, TableCaption, TableContainer } from "@chakra-ui/react";
import { useEffect, useState } from "react";
import { API } from "../../sharedComponent/API";
import axios from "axios";
import { useNavigate, useParams } from "react-router-dom";
import BottomNavbar from "../../Components/Navbar/BottomNavbar";
import Navbar from "../../Components/Navbar/Navbar";

const RideInfo = () => {
    const navigate = useNavigate();
    const { ride_id, is_driver } = useParams();
    const [isLoading, setIsLoading] = useState(null);
    const [loggedInUserDetails, setLoggedInUserDetails] = useState({});
    const [isLargerThan1280] = useMediaQuery('(min-width: 700px)');

    useEffect(() => {
        const loggedInUserInfo = JSON.parse(localStorage.getItem('loggedInUserDetails'));
        if (loggedInUserInfo) {
            setLoggedInUserDetails(loggedInUserInfo);
            const config = {
                headers: { Authorization: `Bearer ${loggedInUserInfo.token}` }
            };
            axios.get(`${API}/request/getRideDetail?rideId=${ride_id}`, config)
                .then((resp) => {
                    if (is_driver) {
                        axios.get(`${API}/request/getRideDetail?rideId=${ride_id}`, config)
                    }

                })
                .catch((err) => {
                    console.log("err in retriving rider activity", err.message);
                }).finally(() => setIsLoading(false));
        }
    }, [])

    return (
        <>
            {isLargerThan1280 ? <Navbar /> : <BottomNavbar />}
            <br />
            <Box p="10px" zIndex="-10">
                <Flex w={["100%", "95%", "75%", "75%"]} h={["85vh", "85vh", "80vh", "80vh"]} m="auto" justifyContent='center' align="top" flexDir={["column", "column", "row", "row"]}>
                    <TableContainer>
                        <Table variant='simple'>
                            <TableCaption>Imperial to metric conversion factors</TableCaption>
                            <Thead>
                                <Tr>
                                    <Th>To convert</Th>
                                    <Th>into</Th>
                                    <Th isNumeric>multiply by</Th>
                                </Tr>
                            </Thead>
                            <Tbody>
                                <Tr>
                                    <Td>inches</Td>
                                    <Td>millimetres (mm)</Td>
                                    <Td isNumeric>25.4</Td>
                                </Tr>
                                <Tr>
                                    <Td>feet</Td>
                                    <Td>centimetres (cm)</Td>
                                    <Td isNumeric>30.48</Td>
                                </Tr>
                                <Tr>
                                    <Td>yards</Td>
                                    <Td>metres (m)</Td>
                                    <Td isNumeric>0.91444</Td>
                                </Tr>
                            </Tbody>
                            <Tfoot>
                                <Tr>
                                    <Th>To convert</Th>
                                    <Th>into</Th>
                                    <Th isNumeric>multiply by</Th>
                                </Tr>
                            </Tfoot>
                        </Table>
                    </TableContainer>
                </Flex>
            </Box>
        </>
    )
}

export default RideInfo