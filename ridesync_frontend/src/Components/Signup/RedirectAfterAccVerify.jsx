/* eslint-disable react-hooks/exhaustive-deps */
import axios from 'axios'
import React from 'react'
import { useEffect } from 'react'
import { API } from '../../sharedComponent/API'
import { useNavigate, useParams } from 'react-router-dom'
import { Center, useToast } from '@chakra-ui/react'
import { CircularProgress } from '@chakra-ui/react'
const RedirectAfterAccVerify = () => {
    const toast = useToast()

    const { id, email } = useParams()
    const navigate = useNavigate()


    useEffect(() => {
        if (!email || !id) {
            window.history.go(-1)
        }
        try {
            axios.get(`${API}/auth/verifyEmail?email=${email}&id=${id}`).then(resp => {
                console.log("rrr", resp.data);
                if (resp.data.success) {
                    toast({
                        title: 'Email verification',
                        description: `${resp.data.message}`,
                        status: 'success',
                        duration: 3000,
                        isClosable: true,
                    })
                    navigate("/login");
                } else {
                    toast({
                        title: 'Email verification',
                        description: `${resp.data.message}`,
                        status: 'error',
                        duration: 3000,
                        isClosable: true,
                    })
                    navigate("/signup");
                }

            })
        } catch (error) {
            console.log("error in Acc verfiy", error.message);
        }

    }, [])

    return (
        <Center h="100vh"><CircularProgress value={30} size='100px' /></Center>
    )
}

export default RedirectAfterAccVerify