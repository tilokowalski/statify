import Cookies from "cookies";

import { CircularProgress } from "@mui/material";
import { GetServerSideProps, NextPage } from "next";

const Index: NextPage = () => {
    return <CircularProgress />;
};

export default Index;

export const getServerSideProps: GetServerSideProps = async ({ req, res }) => {
    const cookies = new Cookies(req, res);

    if (cookies.get('access_token')) {
        return {
            redirect: {
                destination: '/dashboard',
                permanent: false,
            },
        };
    } else {
        return {
            redirect: {
                destination: '/login',
                permanent: false,
            },
        };
    }

    return { props: {} };
};
