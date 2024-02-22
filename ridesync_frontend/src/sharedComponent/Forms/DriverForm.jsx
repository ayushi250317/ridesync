import { Button, Input } from '@chakra-ui/react'
import React, { useState } from 'react'


const DriverForm = ({ onSubmitDriverDetails, loading }) => {
    const [licenseNo, setLicenseNo] = useState("");
    const [expiryDate, setExpiryDate] = useState("");
    return (
        <>
            <Input w="75%" placeholder='License Number' onChange={(e) => setLicenseNo(e.target.value)}></Input>
            <br />
            <Input w="75%"
                placeholder='Expiry Date'
                type="date"
                onChange={(e) => setExpiryDate(e.target.value)}
            ></Input>

            <br />
            <Button
                isLoading={loading}
                w="75%"
                colorScheme='blue'
                onClick={() => { onSubmitDriverDetails(licenseNo, expiryDate) }}>Submit</Button>
            <br />
        </>
    )
}

export default DriverForm