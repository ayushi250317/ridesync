import { Box, Button, Center, Flex, Image, Input, Text, Textarea, useToast } from '@chakra-ui/react'
import axios from 'axios'
import React, { useState } from 'react'
import { Link, useNavigate } from 'react-router-dom'
import { API } from '../../sharedComponent/API'
import { yupResolver } from "@hookform/resolvers/yup"
import * as yup from "yup"
import { useForm } from 'react-hook-form'
import { FaUserEdit } from "react-icons/fa";
const phoneRegExp = /^((\\+[1-9]{1,4}[ \\-]*)|(\\([0-9]{2,3}\\)[ \\-]*)|([0-9]{2,4})[ \\-]*)*?[0-9]{3,4}?[ \\-]*[0-9]{3,4}?$/
const schema = yup
    .object({
        fullName: yup.string().min(3, 'Name is too short.').required("Please enter your name."),
        address: yup.string().required("Please enter the address."),
        dateOfBirth: yup.string().required("Please enter the Date of Birth."),
        phoneNumber: yup.string().min(10, 'Phone number is not valid.').max(10, "Phone number is not valid.").matches(phoneRegExp, 'Phone number is not valid.'),
        email: yup.string().email("Please enter a valid email.").required("Email is required."),
        password: yup.string()
            .required('No password provided.')
            .min(6, 'Password must be minimum of 6 characters.')
        // .matches(/[a-zA-Z]/, 'Password can only contain Latin letters.')
    })
    .required()


const PersonalInfoEdit = () => {

    const {
        register,
        handleSubmit,
        watch,
        formState: { errors },
    } = useForm({
        mode: "onBlur",
        resolver: yupResolver(schema),
    })


    const [userDetails, setUserDetails] = useState({
        "fullName": "",
        "email": "",
        "address": "",
        "dateOfBirth": "",
        "phoneNumber": "",
        "password": ""
    })
    const [loading, setLoading] = useState(false)
    let navigate = useNavigate();
    const toast = useToast();

    const onSubmit = (data) => {
        console.log("dada", data);
        setLoading(true)
        // userDetails is the request body
        console.log(userDetails)
        axios.post(`${API}/auth/register`, userDetails)
            .then(response => {
                console.log('Response:', response);
                if (response.data.success) {

                    navigate('/account_email_verification', { state: { email: response.data.user.email } });
                } else {
                    console.log("sdsa", response.data.message);
                    toast({
                        title: `${response.data.message}`,
                        status: 'error',
                        duration: 5000,
                        isClosable: true,
                    })
                }
            })
            .catch(error => {
                console.error('Error:', error);
            }).finally(() => setLoading(false));
    };

    const registerUser = () => {

    }
    const setUserDetail = (e) => {
        // e is an event object, it would contain name of the input field and value of the input field
        const { name, value } = e.target;
        setUserDetails(prevDetails => ({
            ...prevDetails,
            [name]: value
        }));
    };
    return (
        <Box>
            <br />
            <Flex w={["100%", "95%", "90%", "80%"]} m="auto" justifyContent="center" align="center" flexDir={["column-reverse", "column-reverse", "row", "row"]}>

                <Flex w={["90%", "95%", "60%", "55%"]} justifyContent="center" flexDir="column" >
                    <Center>

                        <FaUserEdit size="55px" />
                    </Center>
                    <Text fontWeight="medium" fontSize="3xl" textAlign="center" >Edit personal details.</Text>
                    <form onSubmit={handleSubmit(onSubmit)}>

                        <Text mb="1">Name</Text>
                        <Input w={["100%", "100%", "90%", "90%"]}
                            placeholder='Enter your name'
                            type='text'
                            name="fullName"
                            {...register("fullName")}
                        ></Input>
                        <Text color="red">{errors.fullName && errors.fullName?.message}</Text>


                        <Text mt="3" mb="1">Phone number</Text>
                        <Input w={["100%", "100%", "90%", "90%"]}
                            placeholder='Enter your phone number'
                            type='number'
                            name="phoneNumber"
                            {...register("phoneNumber")}
                        ></Input>
                        <Text color="red">{errors.phoneNumber && errors.phoneNumber?.message}</Text>

                        <Text mt="3" mb="1">Date of birth</Text>

                        <Input w={["100%", "100%", "90%", "90%"]}
                            placeholder='Date of Birth'
                            type='date'
                            name="dateOfBirth"
                            {...register("dateOfBirth")}
                        ></Input>
                        <Text color="red">{errors.dateOfBirth && errors.dateOfBirth?.message}</Text>

                        <Text mt="3" mb="1">Address</Text>
                        <Textarea w={["100%", "100%", "90%", "90%"]}
                            placeholder='Enter your address'
                            type='text'
                            name="address"
                            {...register("address")}
                        ></Textarea>
                        <Text color="red">{errors.address && errors.address?.message}</Text>


                        <Button w={["100%", "100%", "90%", "90%"]} my="7" type="submit" colorScheme='blue'
                            isLoading={loading}
                        >Submit</Button>
                    </form>
                </Flex>
            </Flex>
        </Box>
    )
}

export default PersonalInfoEdit