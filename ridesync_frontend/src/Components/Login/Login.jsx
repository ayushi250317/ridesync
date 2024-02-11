import { Box, Button, Flex, Image, Input, Text, useToast } from '@chakra-ui/react'
import axios from 'axios'
import React, { useState } from 'react'
import { Link, useNavigate } from 'react-router-dom'
import { API } from '../../sharedComponent/API'


const Login = () => {
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const naviagate = useNavigate();
    const toast = useToast();

    const handleSubmit = () => {
        let requestObj = {
            email: email,
            password: password
        };
        axios.post(`${API}/auth/authenticate`, requestObj)
            .then(response => {
                if (response.data.success) {
                    let { token, user } = response;
                    localStorage.setItem('loggedInUserDetails', { token, user });
                    naviagate("/");
                } else {
                    toast({
                        title: response.data.message,
                        status: 'error',
                        duration: 5000,
                        isClosable: true,
                    });
                    console.log('Response:', response);
                }
            })
            .catch(error => {
                console.error('Error:', error);
            });
    }
    return (
        <Box  >
            <Flex w={["100%", "95%", "90%", "80%"]} h={["", "", "100vh", "100vh"]} m="auto" justifyContent="center" align="center" flexDir={["column", "column", "row", "row"]}>
                <Flex w="50%" borderRight={["", "", "1px solid lightgray", "1px solid lightgray"]}>
                    <Image src='/try2.png' w={["100%", "100%", "85%", "80%"]}></Image>
                </Flex>
                <Flex w={["100%", "95%", "60%", "50%"]} justifyContent="center" align="center" flexDir="column" >
                    <Text fontWeight="medium" fontSize="3xl">Login</Text>
                    <br />
                    <Input w="75%" placeholder='Email' onChange={(e) => setEmail(e.target.value)}></Input>
                    <br />
                    <Input w="75%"
                        placeholder='Password'
                        type='password'
                        onChange={(e) => setPassword(e.target.value)}>
                    </Input>
                    <br />
                    <Button w="75%" colorScheme='blue' onClick={handleSubmit}>Submit</Button>
                    <br />
                    <Flex justifyContent="space-between" w="75%" textAlign="center" flexDir={["column", "column", "column", "row"]}>
                        <Box >

                            <span >Haven't registered yet? <span className='text-blue-700'><Link to="/signup">Click here</Link></span></span>
                        </Box>
                        <Box >
                            <Link to="/forgot_password" className='text-blue-700'>Forgot Password?</Link>
                        </Box>
                    </Flex>
                </Flex>
            </Flex>
        </Box>
    )
}

export default Login