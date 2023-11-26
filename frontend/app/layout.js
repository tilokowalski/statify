
export const metadata = {
    title: 'Statify',
    description: 'Your spotify analytics tool!',
}

export default function RootLayout({ children }) {
  return (
      <html lang="en">
          <body>
                <div style={{ 
                    display: 'flex', 
                    justifyContent: 'center', 
                    alignItems: 'center', 
                    height: '90vh' 
                }}
                >
                    {children}
                </div>
          </body>
      </html>
    )
}
