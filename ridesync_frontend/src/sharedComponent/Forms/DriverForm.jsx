import { Button, Input, FormErrorMessage, FormLabel, FormControl, } from '@chakra-ui/react'
import { useForm } from 'react-hook-form'
import React, { useState } from 'react'


const DriverForm = ({ onSubmitDriverDetails, loading }) => {
    const [licenseNo, setLicenseNo] = useState("");
    const [expiryDate, setExpiryDate] = useState("");
    const {
        handleSubmit,
        register,
        formState: { errors },
    } = useForm();
    const onSubmit = () => {
        onSubmitDriverDetails(licenseNo, expiryDate)
    }
    return (
        <form onSubmit={handleSubmit(onSubmit)} style={{ width: "75%" }}>
            <FormControl isInvalid={errors.lNo}>
                <FormLabel htmlFor='lNo'>License Number</FormLabel>
                <Input w="100%"
                    id='lNo'
                    placeholder='License Number'
                    {...register('lNo', {
                        required: 'license number is required',
                        minLength: { value: 5, message: 'Minimum length should be 5' },
                        onChange: (e) => setLicenseNo(e.target.value)
                    })}
                ></Input>
                <FormErrorMessage>
                    {errors.lNo && errors.lNo.message}
                </FormErrorMessage>
            </FormControl>
            <br />
            <FormControl isInvalid={errors.eDate}>
                <FormLabel htmlFor='eDate'>Expiry Date</FormLabel>
                <Input w="100%"
                    id='eDate'
                    type="date"
                    {...register('eDate', {
                        required: 'Expiry Date is required',
                        onChange: (e) => setExpiryDate(e.target.value)
                    })}
                ></Input>
                <FormErrorMessage>
                    {errors.eDate && errors.eDate.message}
                </FormErrorMessage>
            </FormControl>
            <br />
            <Button
                isLoading={loading}
                w="100%"
                colorScheme='blue'
                type='submit'
            >Submit</Button>
            <br />
        </form>
    )
}

export default DriverForm