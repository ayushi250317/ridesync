import { Button, Input, FormErrorMessage, FormLabel, FormControl, SimpleGrid, Flex, } from '@chakra-ui/react'
import React, { useState } from 'react'
import { useForm } from 'react-hook-form';


const VehicleForm = ({ onSubmitVehicleDetails, loading }) => {
    const [regNo, setRegNo] = useState("");
    const [type, setType] = useState("");
    const [make, setMake] = useState("");
    const [model, setModel] = useState("");
    const [insuranceNo, setInsuranceNo] = useState("");
    const [insuranceExpiry, setInsuranceExpiry] = useState("");
    const {
        handleSubmit,
        register,
        formState: { errors },
    } = useForm();

    const onSubmit = () => {
        let vehicleDetails = {
            regNo: regNo,
            type: type,
            make: make,
            model: model,
            insuranceNo: insuranceNo,
            insuranceExpiry: insuranceExpiry
        }
        onSubmitVehicleDetails(vehicleDetails)
    }

    return (
        <form onSubmit={handleSubmit(onSubmit)} style={{ width: "100%" }}>
            <SimpleGrid columns={{ base: 1, md: 2 }} spacing={5} px="5" py="5" mt="7">
                <FormControl isInvalid={errors.vehicleRegistrationNo}>
                    <FormLabel htmlFor='vehicleRegistrationNo'>Vehicle Registration Number</FormLabel>
                    <Input
                        id='vehicleRegistrationNo'
                        w="100%"
                        onChange={(e) => {
                            setRegNo(e.target.value)
                        }}
                        {...register('vehicleRegistrationNo', {
                            required: 'vehicle Registration Number is required',
                            minLength: { value: 5, message: 'Minimum length should be 5' },
                            onChange: (e) => { setRegNo(e.target.value) }
                        })}
                    >
                    </Input>
                    <FormErrorMessage>
                        {errors.vehicleRegistrationNo && errors.vehicleRegistrationNo.message}
                    </FormErrorMessage>
                </FormControl>
                <FormControl isInvalid={errors.type}>
                    <FormLabel htmlFor='type'>Type</FormLabel>
                    <Input
                        id='type'
                        w="100%"
                        {...register('type', {
                            required: 'Type is incorrect',
                            minLength: { value: 3, message: 'Minimum length should be 3' },
                            onChange: (e) => setType(e.target.value)

                        })}>
                    </Input>
                    <FormErrorMessage>
                        {errors.type && errors.type.message}
                    </FormErrorMessage>
                </FormControl>
                <FormControl isInvalid={errors.make}>
                    <FormLabel htmlFor='make'>Make</FormLabel>
                    <Input
                        id='make'
                        w="100%"
                        {...register('make', {
                            required: 'Make is incorrect',
                            minLength: { value: 3, message: 'Minimum length should be 3' },
                            onChange: (e) => setMake(e.target.value)
                        })}
                    ></Input>
                    <FormErrorMessage>
                        {errors.make && errors.make.message}
                    </FormErrorMessage>
                </FormControl>
                <FormControl isInvalid={errors.model}>
                    <FormLabel htmlFor='model'>Model</FormLabel>
                    <Input
                        id='model'
                        w="100%"
                        {...register('model', {
                            required: 'Model is incorrect',
                            minLength: { value: 3, message: 'Minimum length should be 3' },
                            onChange: (e) => setModel(e.target.value)
                        })}
                    >
                    </Input>
                    <FormErrorMessage>
                        {errors.model && errors.model.message}
                    </FormErrorMessage>
                </FormControl>
                <FormControl isInvalid={errors.insuranceNo}>
                    <FormLabel htmlFor='insuranceNo'>Insurance Number</FormLabel>
                    <Input
                        id="insuranceNo"
                        w="100%"
                        {...register('insuranceNo', {
                            required: 'Insurance Number is incorrect',
                            minLength: { value: 3, message: 'Minimum length should be 3' },
                            onChange: (e) => setInsuranceNo(e.target.value)
                        })}
                    ></Input>
                    <FormErrorMessage>
                        {errors.insuranceNo && errors.insuranceNo.message}
                    </FormErrorMessage>
                </FormControl>
                <FormControl isInvalid={errors.insuranceExpiry}>
                    <FormLabel htmlFor='insuranceExpiry'>Insurance Expiry</FormLabel>
                    <Input
                        id="insuranceExpiry"
                        w="100%"
                        type="date"
                        {...register('insuranceExpiry', {
                            required: 'Expiry Date is required',
                            onChange: (e) => setInsuranceExpiry(e.target.value)
                        })}
                    ></Input>
                    <FormErrorMessage>
                        {errors.insuranceExpiry && errors.insuranceExpiry.message}
                    </FormErrorMessage>
                </FormControl>
            </SimpleGrid>
            <Flex justifyContent="center" w="100%">
                <Button
                    isLoading={loading}
                    w={["92%", "92%", "50%", "50%"]}
                    py="5"
                    colorScheme='blue'
                    type='submit'
                >Submit</Button>
            </Flex>
            <br />

            <br />
            <br />
        </form>
    )
}

export default VehicleForm