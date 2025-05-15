sudo docker system prune -a --volumes -f
sudo journalctl --vacuum-time=1s
sudo apt autoremove --purge -y
sudo apt clean
sudo apt autoclean
sudo df -h
