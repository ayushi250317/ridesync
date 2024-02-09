import {
    Box,
    Button,
    Center,
    Input,
    Text,
    useToast,
} from "@chakra-ui/react";
import axios from "axios";
import React from "react";
import { useEffect } from "react";
import { IoIosKey } from "react-icons/io";
import { useNavigate, useParams } from "react-router-dom";
import { API } from "../../sharedComponent/API";
import { useState } from "react";

const ConfirmPassword = () => {
    const toast = useToast()
    const { token, id } = useParams();
    const [isTrue, setIsTrue] = useState(null);
    const [isLoading, setIsLoading] = useState(null);
    const navigate = useNavigate();
    // const [newPassword,setNewPassword] = useState('');
    const [password, setPassword] = useState({
        newPassword: "",
        reNewPassword: "",
    });
    // console.log({ params });

    useEffect(() => {
        setIsLoading(true)
        axios
            .get(`${API}/auth/resetPassword?token=${token}&id=${id}`)
            .then((resp) => {
                if (resp.data.success) {
                    setIsTrue(true);
                }
            })
            .catch((err) => {
                setIsTrue(false)
                console.log("err in confirm password", err.message);
            }).finally(() => setIsLoading(false));
    }, []);

    const handleChange = (event) => {
        const { name, value } = event.target;
        setPassword(() => {
            return { ...password, [name]: value };
        });
    };

    const handleSubmit = () => {
        //validation for two empty string is left and could be done later  
        if (password.newPassword === password.reNewPassword) {
            axios.post(`${API}/auth/newPassword`, { ...password, token, id }).then(resp => {
                console.log(resp.data);
                if (resp.data.success) {
                    navigate("/login")
                    toast({
                        title: 'Password Reset',
                        description: `${resp.data.message}`,
                        status: 'success',
                        duration: 3000,
                        isClosable: true,
                    })
                }
            }).catch(err => {
                console.log("err in submiting password", err);
            })
        }
        else {
            toast({
                title: 'Password Reset',
                description: "Password doesn't match",
                status: 'error',
                duration: 3000,
                isClosable: true,
            })
            // console.log("password doesnt match");
        }
    };


    if (isLoading) {
        return <><Text>Loading...</Text></>
    }

    if (!isTrue)
        return (
            <>
                <Text>Unauthorized</Text>
            </>
        );

    return (
        <Center h="100vh">
            <Box
                w={["90%", "70%", "60%", "40%"]}
                m="auto"
                border="1px solid lightgray"
                borderRadius="xl"
                boxShadow="2xl"
                p="10"
            >
                <Center>
                    <IoIosKey size="55px" />
                </Center>
                <Text textAlign="center" fontSize={["2xl", "2xl", "3xl", "3xl"]}>
                    Forgot Password?
                </Text>
                <br />
                <form action="
                ">

                    <Text>Enter a new Password</Text>
                    <Input
                        placeholder="password"
                        type="password"
                        name="newPassword"
                        value={password.newPasseord}
                        onChange={handleChange}
                    ></Input>
                    <br />
                    <br />
                    <Text>Re-type new Password</Text>
                    <Input
                        placeholder="confirm password"
                        type="password"
                        name="reNewPassword"
                        value={password.reNewPassword}
                        onChange={handleChange}
                    ></Input>
                    <br />
                    <br />
                    <Button colorScheme="facebook" w="100%" onClick={handleSubmit}>
                        Submit
                    </Button>
                </form>

            </Box>
        </Center>
    );
};

export default ConfirmPassword;
