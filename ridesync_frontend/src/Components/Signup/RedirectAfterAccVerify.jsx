import axios from 'axios'
import React from 'react'
import { useEffect } from 'react'
import { API } from '../../sharedComponent/API'
import { useNavigate, useParams } from 'react-router-dom'
import { Text, useToast } from '@chakra-ui/react'

const RedirectAfterAccVerify = () => {
    const toast = useToast()

    const { id, email } = useParams()
    const navigate = useNavigate()


    useEffect(() => {
        if (!email || !id) {
            window.history.go(-1)
        }
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
                    status: 'success',
                    duration: 3000,
                    isClosable: true,
                })
                navigate("/signup");
            }

        })
    }, [])

    return (
        <div><Text>Please wait...</Text></div>
    )
}

export default RedirectAfterAccVerify