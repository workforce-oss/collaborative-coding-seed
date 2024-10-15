// Path: src/App.tsx

import AccountComponent from './features/accounts/AccountComponent';
import './App.css';
import { AppBar, Button, Container, Toolbar, Typography } from '@mui/material';
import { styled } from '@mui/material/styles';

const AppRoot = styled('div')(({ theme }) => ({
    flexGrow: 1,
}));

function App() {
    return (
        <AppRoot>
            <AppBar position="static">
                <Toolbar>
                    <Typography variant="h6" component="div" sx={{ flexGrow: 1 }}>
                        Account Manager
                    </Typography>
                </Toolbar>
            </AppBar>
            <Container maxWidth="lg">
                <AccountComponent />
            </Container>
        </AppRoot>
    );
}

export default App;
