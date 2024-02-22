import { Button, Input } from '@chakra-ui/react'
import axios from 'axios'
import React, { useState } from 'react'


const VehicleForm = ({ onSubmitVehicleDetails, loading }) => {
    const [vehicleDetails, setVehicleDetails] = useState({
        "regNo": "",
        "type": "",
        "make": "",
        "model": "",
        "insuranceNo:": ""
    })
    // {
    //     "regNo":"NS-54321",
    //     "documentId":203,
    //     "model":"Passenger Train",
    //     "make":"Diesel",
    //     "type":"flight",
    //     "userId":2
    // }
    const setVehicleDetail = (e) => {
        // e is an event object, it would contain name of the input field and value of the input field
        const { name, value } = e.target;
        setVehicleDetails(prevDetails => ({
            ...prevDetails,
            [name]: value
        }));
    };
    return (
        <>
            <Input w="75%" name="regNo" placeholder='Vehicle Registration Number' onChange={setVehicleDetail}></Input>
            <br />
            <Input w="75%" name='type' placeholder='Type' onChange={setVehicleDetail}></Input>
            <br />
            <Input w="75%" name='make' placeholder='Make' onChange={setVehicleDetail}></Input>
            <br />
            <Input w="75%" name='model' placeholder='Model' onChange={setVehicleDetail}></Input>
            <br />
            <Input w="75%" name="insuranceNo" placeholder='Insurance Number' onChange={setVehicleDetail}></Input>
            <br />
            <Input w="75%" name="insuranceExpiry" type="date" placeholder='Insurance Expiry' onChange={setVehicleDetail}></Input>
            <br />
            <Button
                isLoading={loading}
                w="75%"
                colorScheme='blue'
                onClick={() => { onSubmitVehicleDetails(vehicleDetails) }}>Submit</Button>
            <br />
        </>
    )
}

export default VehicleForm