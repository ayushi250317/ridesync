import { Box, Flex, Image, Text } from "@chakra-ui/react";
import React from "react";
import { useNavigate } from "react-router-dom";
import Navbar from "../Navbar/Navbar";
import { useMediaQuery } from '@chakra-ui/react'
import BottomNavbar from "../Navbar/BottomNavbar";

const Home = () => {
    const naviagate = useNavigate();

    const [isLargerThan1280] = useMediaQuery('(min-width: 700px)')

    return (
        <>
            {isLargerThan1280 ? <Navbar /> : <BottomNavbar />}
            {/* <BottomNavbar /> */}
            <br />
            <Flex flexDir="column" w="90%" m="auto" zIndex="-10">
                <Text textAlign="center" fontSize={["3xl", "3xl", "4xl", "5xl"]}>
                    Hey Hodophiles!!
                </Text>
                <br />
                {/* <br /> */}
                <Flex
                    justifyContent="space-evenly"
                    flexDir={["column", "column", "row", "row"]}
                    mb={["10", "10", "", ""]}
                >
                    <Box
                        m="3"
                        onClick={() => naviagate("/rider_registration")}
                        className="transition-all hover:scale-105"
                        border="1px solid lightgray"
                        px={["2", "10", "12", "16"]}
                        py={["4", "4", "6", "10"]}
                        borderRadius="2xl"
                        _hover={{ cursor: "pointer" }}
                        boxShadow="md"
                    >
                        <Text fontSize={["xl", "xl", "xl", "2xl"]} my="7" textAlign="center">
                            Would you like to drive?
                        </Text>

                        <Image
                            src="./driver.png"
                            m="auto"
                            w={["30%", "40%", "60%", "70%"]}
                        ></Image>
                    </Box>
                    <Box
                        m="3"
                        onClick={() => naviagate("/joinride")}
                        className="transition-all hover:scale-105"
                        border="1px solid lightgray"
                        p={["4", "4", "6", "10"]}
                        borderRadius="2xl"
                        _hover={{ cursor: "pointer" }}
                        boxShadow="md"
                    >
                        <Text fontSize={["xl", "xl", "xl", "2xl"]} my="7" textAlign="center">
                            Would you like to have a ride?
                        </Text>
                        <Image
                            src="./rider3.png"
                            m="auto"
                            w={["30%", "40%", "50%", "60%"]}
                        ></Image>
                    </Box>
                </Flex>
            </Flex>
        </>
    );
};

export default Home;
