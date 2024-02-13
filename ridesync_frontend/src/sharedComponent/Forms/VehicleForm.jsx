import { Box, Button, Flex, Image, Input, Text, useToast } from '@chakra-ui/react'
import axios from 'axios'
import React, { useState } from 'react'
import { Link, useNavigate } from 'react-router-dom'


const VehicleForm = ({ onSubmitVehicleDetails, loading }) => {

    return (
        <>
            <Input w="75%" placeholder='Vehicle Number'></Input>
            <br />
            <Input w="75%" placeholder='Vehicle Model'></Input>
            <br />
            <Button
                isLoading={loading}
                w="75%"
                colorScheme='blue'
                onClick={() => { onSubmitVehicleDetails() }}>Submit</Button>
            <br />
        </>
    )
}

export default VehicleForm