import { Box, Button, Flex, Image, Input, Text, useToast } from '@chakra-ui/react'
import axios from 'axios'
import React, { useState } from 'react'
import { Link, useNavigate } from 'react-router-dom'
import { API } from '../../sharedComponent/API'

const Signup = () => {
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

    const registerUser = () => {
        setLoading(true)
        // userDetails is the request body
        console.log(userDetails)
        axios.post(`${API}/auth/register`, userDetails)
            .then(response => {
                console.log('Response:', response);
                if (response.data.success) {
                    // toast({
                    //     title: 'Account created Successfully.',
                    //     status: 'success',
                    //     duration: 5000,
                    //     isClosable: true,
                    // })
                    navigate('/account_email_verification', { state: { email: response.data.user.email } });
                }
            })
            .catch(error => {
                console.error('Error:', error);
            }).finally(() => setLoading(false));
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
            <Flex w={["100%", "95%", "90%", "80%"]} h={["", "", "100vh", "100vh"]} m="auto" justifyContent="center" align="center" flexDir={["column-reverse", "column-reverse", "row", "row"]}>

                <Flex w={["90%", "95%", "60%", "55%"]} justifyContent="center" flexDir="column" >

                    <Text fontWeight="medium" fontSize="3xl" textAlign="center">Create account</Text>
                    <Text mb="1">Name</Text>
                    <Input w={["100%", "100%", "90%", "90%"]}
                        placeholder='Enter your name'
                        type='text'
                        name="fullName"
                        value={userDetails.fullName}
                        onChange={setUserDetail}></Input>
                    <Text mt="3" mb="1">Email</Text>

                    <Input w={["100%", "100%", "90%", "90%"]}
                        placeholder='Enter your email'
                        type='email'
                        name="email"
                        value={userDetails.email}
                        onChange={setUserDetail}></Input>
                    <Text mt="3" mb="1">Address</Text>
                    <Input w={["100%", "100%", "90%", "90%"]}
                        placeholder='Enter your address'
                        type='text'
                        name="address"
                        value={userDetails.address}
                        onChange={setUserDetail}></Input>
                    <Text mt="3" mb="1">Date of birth</Text>

                    <Input w={["100%", "100%", "90%", "90%"]}
                        placeholder='Date of Birth'
                        type='date'
                        name="dateOfBirth"
                        value={userDetails.dateOfBirth}
                        onChange={setUserDetail}></Input>
                    <Text mt="3" mb="1">Phone number</Text>
                    <Input w={["100%", "100%", "90%", "90%"]}
                        placeholder='Enter your phone number'
                        type='number'
                        name="phoneNumber"
                        value={userDetails.phoneNumber}
                        onChange={setUserDetail}></Input>
                    <Text mt="3" mb="1">Password</Text>
                    <Input w={["100%", "100%", "90%", "90%"]}
                        placeholder='Enter your password'
                        type='password'
                        name="password"
                        value={userDetails.password}
                        onChange={setUserDetail}></Input>
                    <br />
                    <Button w={["100%", "100%", "90%", "90%"]} colorScheme='blue' onClick={registerUser}
                        isLoading={loading}
                    >Submit</Button>

                    <br />
                    <span >Already have an account? <span className='text-blue-700'><Link to="/login">Click here</Link></span></span>
                </Flex>
                <Flex w="45%" >
                    <Image src='/images.png' w={["100%", "100%", "85%", "80%"]}></Image>
                </Flex>
            </Flex>
        </Box>
    )
}

export default Signup