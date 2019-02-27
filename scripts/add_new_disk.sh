set -e
set -x

if [ -f /etc/disk_added_date ]
then
   echo "disk already added so exiting."
   exit 0
fi
disk=/dev/sdb

sudo fdisk -u ${disk} <<EOF
n
p
1


t
8e
w
EOF


 pvcreate ${disk}1
 #vgcreate VolGroup ${disk}1
 vgcreate data_vg ${disk}1
 #lvextend -l +100%FREE /dev/VolGroup/lv_root
 #resize2fs /dev/VolGroup/lv_root
date > /etc/disk_added_date