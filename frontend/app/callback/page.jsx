
'use client';

import { CircularProgress, Typography } from "@mui/material";
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
        <div style={{ 
            display: 'flex', 
            justifyContent: 'center', 
            alignItems: 'center', 
            height: '90vh' 
          }}
        >
            <Typography variant="h6" component="div" style={{ marginBottom: "20px" }}>
                Please wait while we log you in...
            </Typography>
            <CircularProgress />
        </div>
    );
}
