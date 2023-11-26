
import { Container } from '@mui/material';

export const metadata = {
    title: 'Statify',
    description: 'Your spotify analytics tool!',
}

export default function RootLayout({ children }) {
  return (
      <html lang="en">
          <body>
                <Container>
                    {children}
                </Container>
          </body>
      </html>
    )
}
