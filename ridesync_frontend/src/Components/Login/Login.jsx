import { Box, Button, Flex, Image, Input, InputGroup, InputRightElement, Text, useToast } from '@chakra-ui/react'
import axios from 'axios'
import React, { useState } from 'react'
import { Link, useNavigate } from 'react-router-dom'
import { API } from '../../sharedComponent/API'
import { AiOutlineEye, AiOutlineEyeInvisible } from "react-icons/ai";
import { yupResolver } from "@hookform/resolvers/yup"
import { useForm } from "react-hook-form"
import * as yup from "yup"

const schema = yup
    .object({
        email: yup.string().email("Please enter a valid email.").required("Email is required."),
        password: yup.string()
            .required('No password provided.')
            .min(6, 'Password must be minimum of 6 characters.')
    })
    .required()

const Login = () => {
    const [loading, setLoading] = useState(false);
    const naviagate = useNavigate();
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
        axios.post(`${API}/auth/authenticate`, data)
            .then(response => {
                if (response.data.success) {
                    let { token, user, documents, vehicles } = response.data;
                    localStorage.setItem('loggedInUserDetails', JSON.stringify({ token, user, documents, vehicles }));
                    naviagate("/");
                } else {
                    toast({
                        title: response.data.message,
                        status: 'error',
                        duration: 5000,
                        isClosable: true,
                    });
                }
            })
            .catch(error => {
                console.error('Error:', error);
            }).finally(() => {
                setLoading(false)
            });
    };

    const [show, setShow] = useState(false);
    const handleClick = () => setShow(!show);

    return (
        <Box  >
            <Flex w={["100%", "95%", "90%", "80%"]} h={["", "", "100vh", "100vh"]} m={["", "", "auto", "auto"]} mt={["20", "20", "", ""]} justifyContent="center" align="center" flexDir={["column", "column", "row", "row"]}>
                <Flex w="50%" borderRight={["", "", "1px solid lightgray", "1px solid lightgray"]}>
                    <Image src='/try2.png' w={["100%", "100%", "85%", "80%"]}></Image>
                </Flex>
                <Flex mx={["", "", "32", "32"]} justifyContent="space-between" w={["", "", "", "30%"]}>
                    <form onSubmit={handleSubmit(onSubmit)} style={{ width: "100%" }}>
                        <Text fontSize={["4xl", "4xl", "5xl", "5xl"]} mb="5" textAlign="center">
                            Login
                        </Text>
                        <Input
                            w="100%"
                            className="placeholder"
                            border="1px solid gray"
                            placeholder="Email"
                            {...register("email")}
                            my="3"
                        />
                        <Text color="red">{errors.email && errors.email?.message}</Text>
                        <InputGroup size="md">
                            <Input
                                className="placeholder"
                                border="1px solid gray"
                                my="3"
                                {...register("password")}
                                pr="4.5rem"
                                type={show ? "text" : "password"}
                                placeholder="Password"
                            />
                            <InputRightElement width="4.5rem" my="3">
                                <Box
                                    h="1.90rem"
                                    _hover={{ cursor: "pointer" }}
                                    mt="3"
                                    onClick={handleClick}
                                    backgroundColor="transparent"
                                >
                                    {show ? <AiOutlineEyeInvisible /> : <AiOutlineEye />}
                                </Box>
                            </InputRightElement>
                        </InputGroup>
                        <Text color="red">
                            {errors.password && errors.password?.message}
                        </Text>
                        <Button
                            type="submit"
                            w="100%"
                            my="3"
                            colorScheme="blue"
                            isLoading={loading}
                        >
                            Submit
                        </Button>
                        <br />
                        <Box justifyContent="space-between" w="100%" textAlign="center" flexDir={["column", "column", "column", "row"]}>
                            <Box >

                                <span >Haven't registered yet? <span className='text-blue-700'><Link to="/signup">Click here</Link></span></span>
                            </Box>
                            <Box >
                                <Link to="/forgot_password" className='text-blue-700'>Forgot Password?</Link>
                            </Box>
                        </Box>

                    </form>
                </Flex>
            </Flex>
        </Box>
    )
}

export default Login

// <Flex
//             className="bck"
//             justifyContent="center"
//             alignItems="center"
//             flexDir="column"
//             height="100vh"
//         >
//             <Flex
//                 w={["92%", "92%", "90%", "90%"]}
//                 m="auto"
//                 flexDirection={["column-reverse", "column-reverse", "row", "row"]}
//                 className="glass"
//                 color="black"
//                 justifyContent="space-between"
//                 alignItems="center"
//                 border="1px solid lightgray"
//                 borderRadius="2xl"
//             >
//                 <Box mx={["", "", "32", "32"]} p="2">
//                     <form onSubmit={handleSubmit(onSubmit)}>
//                         <Text fontSize={["4xl", "4xl", "5xl", "5xl"]} mb="5" textAlign="center">
//                             Login
//                         </Text>
//                         <Input
//                             className="placeholder"
//                             border="2px solid black"
//                             placeholder="User name"
//                             {...register("name")}
//                             my="3"
//                         />
//                         <Text color="red">{errors.name && errors.name?.message}</Text>
//                         <InputGroup size="md">
//                             <Input
//                                 className="placeholder"
//                                 border="2px solid black"
//                                 my="3"
//                                 {...register("password")}
//                                 pr="4.5rem"
//                                 type={show ? "text" : "password"}
//                                 placeholder="Enter password"
//                             />
//                             <InputRightElement width="4.5rem" my="3">
//                                 <Box
//                                     h="1.90rem"
//                                     _hover={{ cursor: "pointer" }}
//                                     mt="3"
//                                     onClick={handleClick}
//                                     backgroundColor="transparent"
//                                 >
//                                     {show ? <AiOutlineEyeInvisible /> : <AiOutlineEye />}
//                                 </Box>
//                             </InputRightElement>
//                         </InputGroup>
//                         <Text color="red">
//                             {errors.password && errors.password?.message}
//                         </Text>
//                         <Button
//                             type="submit"
//                             w="100%"
//                             my="3"
//                             colorScheme="blue"
//                             isLoading={loading}
//                         >
//                             Submit
//                         </Button>
//                     </form>
//                 </Box>
//                 <Box p="2" borderLeft="1px solid lightgray">
//                     <Image src="/swansealogo.svg"></Image>
//                 </Box>
//             </Flex>
//             <ToastContainer />
//         </Flex>