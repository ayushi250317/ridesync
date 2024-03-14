/* eslint-disable react-hooks/exhaustive-deps */
import {
    Box,
    Button,
    Center,
    Input,
    Spinner,
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
import { yupResolver } from "@hookform/resolvers/yup"
import { useForm } from "react-hook-form"
import * as yup from "yup"

const schema = yup.object({
    newPassword: yup.string().min(6, 'Password must be minimum of 6 character.').required('Password is required'),
    reNewPassword: yup.string().min(6, 'Password must be minimum of 6 characters.')
        .oneOf([yup.ref('newPassword'), null], 'Passwords must match')
});

const ConfirmPassword = () => {
    const toast = useToast()
    const { token, id } = useParams();
    const [isTrue, setIsTrue] = useState(null);
    const [isLoading, setIsLoading] = useState(null);
    const navigate = useNavigate();


    const {
        register,
        handleSubmit,
        formState: { errors },
    } = useForm({
        resolver: yupResolver(schema),
        mode: "onChange"
    })


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

    const onSubmit = (data) => {
        console.log("dataonSubmit", data);
        setIsLoading(true);
        axios.post(`${API}/auth/newPassword`, { ...data, token, id }).then(resp => {
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
        }).finally(() => {
            setIsLoading(false)
        });
    };


    if (isLoading) {
        return <Center h="80vh"><Spinner
            thickness='4px'
            speed='0.65s'
            emptyColor='gray.200'
            color='blue.500'
            size='xl'
        /></Center>
    }

    if (!isTrue)
        return (
            <>
                <Text>Unauthorized</Text>
            </>
        );
    const back = "<"
    return (
        <Center h="100vh">
            <Button position="absolute" top="8" left="8" backgroundColor="transparent" fontSize="2xl"
                onClick={() => navigate("/login")}
            > {back}  </Button>

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
                <form onSubmit={handleSubmit(onSubmit)}>

                    <Text mb="1">Enter a new Password</Text>
                    <Input
                        placeholder="Password"
                        type="password"
                        name="newPassword"
                        {...register("newPassword")}
                    ></Input>
                    <Text color="red">{errors.newPassword && errors.newPassword?.message}</Text>

                    <br />
                    <br />
                    <Text mb="1">Re-type new Password</Text>
                    <Input
                        placeholder="Confirm password"
                        type="password"
                        name="reNewPassword"
                        {...register("reNewPassword")}
                    ></Input>
                    <Text color="red">{errors.reNewPassword && errors.reNewPassword?.message}</Text>

                    <br />
                    <br />
                    <Button colorScheme="facebook" w="100%" type="submit">
                        Submit
                    </Button>
                </form>

            </Box>
        </Center>
    );
};

export default ConfirmPassword;
