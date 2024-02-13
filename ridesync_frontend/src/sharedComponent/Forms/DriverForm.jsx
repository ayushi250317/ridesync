import { Box, Button, Flex, Image, Input, Text, useToast } from '@chakra-ui/react'
import axios from 'axios'
import React, { useState } from 'react'
import { Link, useNavigate } from 'react-router-dom'


const DriverForm = ({ onSubmitDriverDetails, loading }) => {

    return (
        <>
            <Input w="75%" placeholder='License Number'></Input>
            <br />
            <Input w="75%" placeholder='Age'></Input>

            <br />
            <Button
                isLoading={loading}
                w="75%"
                colorScheme='blue'
                onClick={() => { onSubmitDriverDetails() }}>Submit</Button>
            <br />
        </>
    )
}

export default DriverForm