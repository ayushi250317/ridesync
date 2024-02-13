import {
    Box, Button, Flex, Image, Input, Text, useToast, Step,
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
import React, { useState } from 'react'
import { Link, useNavigate } from 'react-router-dom'
import { API } from '../../sharedComponent/API'
import DriverForm from '../../sharedComponent/Forms/DriverForm'
import BottomNavbar from "../Navbar/BottomNavbar";
import Navbar from "../Navbar/Navbar";
import VehicleForm from '../../sharedComponent/Forms/VehicleForm'


const RiderRegistration = () => {
    const [isLargerThan1280] = useMediaQuery('(min-width: 700px)');
    const [loading, setLoading] = useState(false);
    // const naviagate = useNavigate();
    const toast = useToast();
    const steps = [
        { title: 'Driver Info' },
        { title: 'Vehicle Registration' },
    ]
    const { activeStep, setActiveStep } = useSteps({
        index: 0,
        count: steps.length,
    })

    const onSubmitDriverDetails = () => {
        setLoading(true);
        setActiveStep(1);
        setLoading(false);
    }

    const onSubmitVehicleDetails = () => {
        setLoading(true);

        setLoading(false);
    }

    return (
        <>
            {isLargerThan1280 ? <Navbar /> : <BottomNavbar />}
            {/* <BottomNavbar /> */}
            <br />
            <Box p="10px" zIndex="-10">
                <Flex w={["100%", "95%", "90%", "90%"]} h={["", "", "80vh", "80vh"]} m="auto" justifyContent="center" align="center" flexDir={["column", "column", "row", "row"]}>
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
                    <Flex w={["100%", "95%", "60%", "50%"]} justifyContent="center" align="center" flexDir="column" >
                        {activeStep == 0 && < DriverForm onSubmitDriverDetails={onSubmitDriverDetails} loading={loading} />}
                        {activeStep == 1 && < VehicleForm onSubmitVehicleDetails={onSubmitVehicleDetails} loading={loading} />}
                    </Flex>
                </Flex>
            </Box>
        </>
    )
}

export default RiderRegistration