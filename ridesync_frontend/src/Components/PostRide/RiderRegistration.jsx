import {
    Box, Flex, useToast, Step,
    StepDescription,
    StepIcon,
    StepIndicator,
    StepNumber,
    StepSeparator,
    StepStatus,
    StepTitle,
    Stepper,
    useSteps,
    useMediaQuery,
} from '@chakra-ui/react'
import axios from 'axios'
import React, { useEffect, useState } from 'react'
import { Link, useNavigate } from 'react-router-dom'
import { API } from '../../sharedComponent/API'
import DriverForm from '../../sharedComponent/Forms/DriverForm'
import BottomNavbar from "../Navbar/BottomNavbar";
import Navbar from "../Navbar/Navbar";
import VehicleForm from '../../sharedComponent/Forms/VehicleForm'


const RiderRegistration = () => {
    const [isLargerThan1280] = useMediaQuery('(min-width: 700px)');
    const [loading, setLoading] = useState(false);
    const [loggedInUserDetails, setLoggedInUserDetails] = useState({});
    const naviagate = useNavigate();
    const toast = useToast();
    const steps = [
        { title: 'Driver Info' },
        { title: 'Vehicle Registration' },
    ]
    const { activeStep, setActiveStep } = useSteps({
        index: 0,
        count: steps.length,
    })

    useEffect(() => {
        const loggedInUserInfo = JSON.parse(localStorage.getItem('loggedInUserDetails'));
        if (loggedInUserInfo) {
            setLoggedInUserDetails(loggedInUserInfo);
        }
    }, []);

    const onSubmitDriverDetails = (licenseNo, expiryDate) => {
        setLoading(true);
        const config = {
            headers: { Authorization: `Bearer ${loggedInUserDetails.token}` }
        };
        let requestObj = {
            "userDocumentID": licenseNo,
            "userId": loggedInUserDetails.user.userId,
            "documentType": "Driving Licence",
            "expiryDate": expiryDate
        }

        axios.post(`${API}/document/addDocument`, requestObj, config)
            .then(response => {
                if (response.data.success) {
                    setLoading(false);
                    setActiveStep(1);
                } else {
                    toast({
                        title: response.data.message,
                        status: 'error',
                        duration: 5000,
                        isClosable: true,
                    });
                    setLoading(false);
                    console.log('Response:', response);
                }
            })
            .catch(error => {
                console.error('Error:', error);
            });
    }

    const onSubmitVehicleDetails = ({ regNo, type, make, model, insuranceNo, insuranceExpiry }) => {
        setLoading(true);
        const config = {
            headers: { Authorization: `Bearer ${loggedInUserDetails.token}` }
        };
        let insuranceReqObj = {
            "userDocumentID": insuranceNo,
            "userId": loggedInUserDetails.user.userId,
            "documentType": "Insurance",
            "expiryDate": insuranceExpiry
        }
        axios.post(`${API}/document/addDocument`, insuranceReqObj, config)
            .then(response => {
                if (response.data.success) {
                    console.log(response.data)
                    let vehicleInfoReqObj = {
                        regNo,
                        model,
                        make,
                        type,
                        userId: loggedInUserDetails.user.userId,
                        documentId: ""
                    }
                    axios.post(`${API}/vehicle/addVehicle`, vehicleInfoReqObj, config)
                        .then(response => {
                            if (response.data.success) {
                                setLoading(false);
                                naviagate("/add_ride")

                            } else {
                                toast({
                                    title: response.data.message,
                                    status: 'error',
                                    duration: 5000,
                                    isClosable: true,
                                });
                                setLoading(false);
                                console.log('Response:', response);
                            }
                        })
                        .catch(error => {
                            console.error('Error:', error);
                        });
                } else {
                    toast({
                        title: response.data.message,
                        status: 'error',
                        duration: 5000,
                        isClosable: true,
                    });
                    setLoading(false);
                    console.log('Response:', response);
                }
            })
            .catch(error => {
                console.error('Error:', error);
            });
    }

    return (
        <>
            {isLargerThan1280 ? <Navbar /> : <BottomNavbar />}
            <br />
            <Box p="10px" zIndex="-10">
                <Flex w={["100%", "95%", "90%", "90%"]} h={["85vh", "85vh", "80vh", "80vh"]} m="auto" justifyContent={isLargerThan1280 ? 'center' : 'flex-start'} align="center" flexDir={["column", "column", "row", "row"]}>
                    <Flex w={["100%", "100%", "20%", "20%"]}>
                        <Stepper
                            orientation={isLargerThan1280 ? 'vertical' : 'horizontal'}
                            size={isLargerThan1280 ? 'lg' : 'md'}
                            index={activeStep}
                            w={["100%", "100%", "100%", "100%"]}
                            h={["", "", "40vh", "40vh"]}
                            marginBottom={["20px", "20px", "", ""]}>
                            {steps.map((step, index) => (
                                <Step key={index}>
                                    <StepIndicator>
                                        <StepStatus
                                            complete={<StepIcon />}
                                            incomplete={<StepNumber />}
                                            active={<StepNumber />}
                                        />
                                    </StepIndicator>

                                    <Box flexShrink='0'>
                                        <StepTitle>{step.title}</StepTitle>
                                        <StepDescription>{step.description}</StepDescription>
                                    </Box>

                                    <StepSeparator />
                                </Step>
                            ))}
                        </Stepper>
                    </Flex>
                    <Flex w={["100%", "95%", "60%", "50%"]} h={["80vh", "80vh", "", ""]} justifyContent="center" align="center" flexDir="column" >
                        {activeStep == 0 && < DriverForm onSubmitDriverDetails={onSubmitDriverDetails} loading={loading} />}
                        {activeStep == 1 && < VehicleForm onSubmitVehicleDetails={onSubmitVehicleDetails} loading={loading} />}
                    </Flex>
                </Flex>
            </Box>
        </>
    )
}

export default RiderRegistration