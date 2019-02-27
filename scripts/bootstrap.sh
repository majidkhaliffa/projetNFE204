
#Création des volumes logiques
lvcreate -L 12288M -n lv_logiciels data_vg
lvcreate -L 8192M -n lv_appli data_vg
lvcreate -L 4096M -n lv_varprojects data_vg

#Formatage des volumes précédemment crées
mkfs.ext3 /dev/data_vg/lv_logiciels
mkfs.ext3 /dev/data_vg/lv_appli
mkfs.ext3 /dev/data_vg/lv_varprojects

#Mise à jour du fichier /etc/fstab pour rajouter les partitions créées
echo "/dev/data_vg/lv_logiciels /logiciels ext3 defaults 1 2
/dev/data_vg/lv_appli /appli ext3 defaults 1 2
/dev/data_vg/lv_varprojects /var/projects ext3 defaults 1 2" >>/etc/fstab

#Montage des partitions
mkdir -p /logiciels
mount /logiciels
mkdir -p /appli
mount /appli
mkdir -p /var/projects
mount /var/projects

#Copy the version_ref file
mkdir -p /etc/conf_machine
cp -f /vagrant/files/version_ref /etc/conf_machine
