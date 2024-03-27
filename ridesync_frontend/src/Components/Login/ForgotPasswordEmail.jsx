/* eslint-disable no-unused-vars */
import { Box, Button, Center, Image, Input, Text, useToast } from '@chakra-ui/react'
import axios from 'axios';
import React from 'react'
import { useState } from 'react';
import { IoChevronBackSharp } from "react-icons/io5";
import { API } from '../../sharedComponent/API';
import { useForm } from "react-hook-form"
import { yupResolver } from "@hookform/resolvers/yup"
import * as yup from "yup"

const schema = yup
    .object({
        email: yup.string().email("Please enter a valid email").required("Email is required"),
    })
    .required()
const ForgotPasswordEmail = () => {

    const [email, setEmail] = useState('')
    const [sentSuccess, setSentSuccess] = useState(false)
    const [loading, setLoading] = useState(false)
    const toast = useToast();

    const {
        register,
        handleSubmit,
        formState: { errors },
    } = useForm({
        resolver: yupResolver(schema),
    })

    const onSubmit = (data) => {
        setLoading(true);
        axios.post(`${API}/auth/forgotPassword`, data).then(resp => {
            if (resp.data?.success) {
                setSentSuccess(true)
            }
            else {
                toast({
                    title: 'Reset Password.',
                    description: `${resp.data.message}`,
                    status: 'error',
                    duration: 5000,
                    isClosable: true,
                })
            }
        }).catch(err => {
            console.log("error", err);
        }).finally(() => {
            setLoading(false)
        });
    };

    return (
        <Center h="100vh" className="flip-card">


            <Box border="1px solid lightgray" padding="5" px="7" borderRadius="xl" boxShadow="xl" w={["90%", "80%", "45%", "35%"]} className="flip-card-inner">
                {!sentSuccess ? <Box className="flip-card-front">

                    <IoChevronBackSharp onClick={() => window.history.go(-1)} className='hovercursor' />
                    <Text textAlign="center" fontSize="2xl" fontWeight="semibold">Reset Password</Text>
                    <Text fontSize="large" my="2">To reset your password, enter the email address you use to signin to Ridesync.</Text>
                    <br />
                    <form onSubmit={handleSubmit(onSubmit)}>

                        <Input placeholder='Enter you email'
                            {...register("email")}
                        ></Input>
                        <Text color="red">{errors.email && errors.email?.message}</Text>

                        <Button isLoading={loading} w="100%" my="4" colorScheme='blue' type="submit">Submit</Button>
                    </form>

                </Box> :
                    <Box className='flip-card-back' padding="5" px="7" backgroundColor="#4267B2" color="white" borderRadius="xl" boxShadow="2xl">
                        <Text textAlign="center" fontSize="3xl">Password link sent.</Text>
                        <Box border="1px soild white">

                            <Image src='./check.gif' w="40%" m="auto"></Image>
                        </Box>
                        <Text textAlign="center" fontSize="xl">Please check your inbox {email}</Text>
                    </Box>}

            </Box>
        </Center>
    )
}

export default ForgotPasswordEmail