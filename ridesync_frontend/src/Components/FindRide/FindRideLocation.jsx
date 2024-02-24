import { Box, Center, Flex, Input, Text } from '@chakra-ui/react'
import React, { useState } from 'react'
import Locations from '../../sharedComponent/Maps/Locations'
import { MdAccessTimeFilled } from "react-icons/md";
const FindRideLocation = () => {
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
    const [startTime, setStartTime] = useState("")
    console.log({ fromAddress, toAddress, startTime });



    return (
        <Box w={["88%", "92%", "63%", "67%"]} m="auto">

            <Center flexDir="column" mt="5" >
                <Text textAlign="center" fontSize="4xl" mb="3">Find Rides</Text>

                <Locations fromAddress={fromAddress} toAddress={toAddress} setFromAddress={setFromAddress} setToAddress={setToAddress} />

                <Flex w="100%" justifyContent="center" alignItems="center" ml="2">

                    <Box mr="2">
                        <MdAccessTimeFilled size="24px" />
                    </Box>
                    <Input type='datetime-local' placeholder='Enter start time' w="96.5%" value={startTime} onChange={(e) => setStartTime(e.target.value)}></Input>
                </Flex>

            </Center>
        </Box>
    )
}

export default FindRideLocation