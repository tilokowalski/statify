
'use client';

import { CircularProgress } from "@mui/material";
import { useSearchParams } from "next/navigation";
import { useEffect } from "react";

const baseUrl = process.env.NEXT_PUBLIC_APPLICATION_BASE_URL;

export default function Callback() {
    const searchParams = useSearchParams();

    const code = searchParams.get('code');

    useEffect(async () => {
        await fetch(baseUrl + '/api/callback?code=' + code, {
            method: 'POST'
        });
        
        window.location.href = "/"
    });

    return (
        <div>
            <CircularProgress />
        </div>
    );
}
